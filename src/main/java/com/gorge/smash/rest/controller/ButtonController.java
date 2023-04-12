package com.gorge.smash.rest.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.model.entity.ButtonPress;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ButtonPressRepository;
import com.gorge.smash.rest.repository.ButtonRepository;
import com.gorge.smash.service.interf.ButtonService;
import com.gorge.smash.util.TempoError;

import io.swagger.v3.oas.annotations.Operation;
import retrofit2.http.Body;

@RestController
@RequestMapping("/buttons")
public class ButtonController extends RestControllerBase
{

	@PostConstruct
	public void init()
	{
		LOGGER = LoggerFactory.getLogger(ButtonController.class);
	}

	@Autowired
	ButtonService buttonService;

	@Autowired
	ButtonRepository buttonRepo;

	@Autowired
	ButtonPressRepository buttonPressRepo;

	@Operation(summary = "Add a button with the name {name} in DB")
	@RequestMapping(path = "/{name}", method = RequestMethod.POST)
	public Button addButton(@PathVariable("name") String name) throws GorgePasContentException
	{
		if (buttonRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button already exists");
		Button b = new Button(name, 0);
		return b = buttonRepo.save(b);
	}

	@Operation(summary = "Increment the button {name} (fast version)")
	@RequestMapping(path = "/{name}/incr", method = RequestMethod.PUT)
	public void incrButton(@PathVariable("name") String name) throws GorgePasContentException
	{
		buttonRepo.incrButtonCount(name);
	}

	@Operation(summary = "Get all buttons and their counts")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Button> getButtons() throws GorgePasContentException
	{
		return buttonRepo.findAll();
	}

	@Operation(summary = "Reset all buttons counts")
	@RequestMapping(path = "", method = RequestMethod.DELETE)
	public void resetCounters() throws GorgePasContentException
	{
		buttonService.resetCounters();
		return;
	}

	/////////////////////////////// BUTTON PRESS ///////////////////////////////////

	@Operation(summary = "Save a button press in DB with IP and Timestamp")
	@RequestMapping(path = "/{name}/press", method = RequestMethod.POST)
	public ButtonPress addButtonPress(@PathVariable("name") String name, @RequestBody ButtonPress press,
			HttpServletRequest request) throws GorgePasContentException
	{
		if (!buttonRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button does not exist");

		// Timestamp ts = new Timestamp(press.getTimestamp());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());
		press.setDate(date);
		press.setIp(request.getRemoteAddr());
		press.setName(name);

		return buttonPressRepo.save(press);
	}
	
	@Operation(summary = "Add a button press and  timestamp  of  receiving  in  DB")
	@RequestMapping(path = "/{name}/tempo", method = RequestMethod.POST)
	public ButtonPress addAdminTempo(@PathVariable("name") String name, @RequestBody ButtonPress press,
			HttpServletRequest request) throws GorgePasContentException
	{
		if (!buttonRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button does not exist");

		// Timestamp ts = new Timestamp(press.getTimestamp());
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		Date date = new Date(ts.getTime());
		press.setDate(date);
		press.setName(name);

		return buttonPressRepo.save(press);
	}
	
	@Operation(summary = "Compute the error in tempo between the crowd and the admin")
	@RequestMapping(path = "/{name}/tempo/error", method = RequestMethod.GET)
	public TempoError getPublicTempoError(@PathVariable("name") String name,
			HttpServletRequest request) throws GorgePasContentException
	{
		return buttonService.getPublicError(name);
	}
	
	
	@Operation(summary = "Get all button presses and their details")
	@RequestMapping(path = "/press/all", method = RequestMethod.GET)
	public List<ButtonPress> getAllPress() throws GorgePasContentException
	{
		return buttonPressRepo.findAll();
	}
	
	@Operation(summary = "Get all button presses between  two timestamps")
	@RequestMapping(path = "/press/between/{start}/{end}", method = RequestMethod.GET)
	public List<ButtonPress> getAllPressBetween(@PathVariable("start") Long start, @PathVariable("end") Long end) throws GorgePasContentException
	{
		if(start == null || end == null || start > end)
			throw new GorgePasContentException(HttpStatus.BAD_REQUEST, "Start and End muist be non null Long with End being greater than Start");
		return buttonPressRepo.findByTimestampBetween(start, end);
	}

	@Operation(summary = "Delete all buttons presses")
	@RequestMapping(path = "/press/all", method = RequestMethod.DELETE)
	public void deleteAllPress() throws GorgePasContentException
	{
		buttonPressRepo.deleteAll();
		return;
	}

	@RequestMapping(path = "/{name}/fast", method = RequestMethod.POST)
	public ButtonPress checkFast(@PathVariable("name") String name, @Body ButtonPress press)
			throws GorgePasContentException
	{
		if (buttonPressRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, name);

		return buttonPressRepo.save(press);
	}

}
