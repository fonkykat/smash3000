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

	@RequestMapping(path = "/{name}", method = RequestMethod.POST)
	public Button addButton(@PathVariable("name") String name) throws GorgePasContentException
	{
		if (buttonRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button already exists");
		Button b = new Button(name, 0);
		return b = buttonRepo.save(b);
	}

	@RequestMapping(path = "/{name}/incr", method = RequestMethod.PUT)
	public void incrButton(@PathVariable("name") String name) throws GorgePasContentException
	{
		buttonRepo.incrButtonCount(name);
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Button> getButtons() throws GorgePasContentException
	{
		return buttonRepo.findAll();
	}

	@RequestMapping(path = "", method = RequestMethod.DELETE)
	public void resetCounters() throws GorgePasContentException
	{
		buttonService.resetCounters();
		return;
	}

	/////////////////////////////// BUTTON PRESS ///////////////////////////////////

	@RequestMapping(path = "/{name}/press", method = RequestMethod.POST)
	public ButtonPress addButtonPress(@PathVariable("name") String name, @RequestBody ButtonPress press,
			HttpServletRequest request) throws GorgePasContentException
	{
		if (!buttonRepo.existsByName(name))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This button does not exist");

		Timestamp ts = new Timestamp(press.getTimestamp());
		Date date = new Date(ts.getTime());
		press.setDate(date);
		press.setIp(request.getRemoteAddr());
		press.setName(name);

		return buttonPressRepo.save(press);
	}

	@RequestMapping(path = "/press/all", method = RequestMethod.GET)
	public List<ButtonPress> getAllPress() throws GorgePasContentException
	{
		return buttonPressRepo.findAll();
	}

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
