package com.gorge.smash.rest.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
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

import com.gorge.smash.model.entity.Concert;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ConcertRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/concert")
public class ConcertController extends RestControllerBase {

	@PostConstruct
	public void init() {
		LOGGER = LoggerFactory.getLogger(ConcertController.class);
	}

	@Autowired
	ConcertRepository concertsRepo;
	
	private String getTodayString()
	{
		LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateObj.format(formatter);
	}
	
	private Date getTodayDate()
	{
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(getTodayString());
		} catch (ParseException e) {
			// TODO Think of a better alternative than today in case of Exception
			return new Date();
		}
	}
	
	@Operation(summary = "Add a new Concert")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Concert addConcert(@RequestBody Concert concert) throws GorgePasContentException {
	
		return concertsRepo.save(concert);
	}
	
	@Operation(summary = "Delete a Concert by Id")
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteConcert(@PathVariable("id") Long id) throws GorgePasContentException {
	
		concertsRepo.deleteById(id);
	}
	
	@Operation(summary = "Update a Concert by Id")
	@RequestMapping(path = "/update/{id}", method = RequestMethod.PUT)
	public Concert updateConcert(@PathVariable("id") Long id, @RequestBody Concert concert) throws GorgePasContentException {
	
		Concert concertToUpdate = concertsRepo.findById(id).orElse(null);
		
		if(concertToUpdate == null)
		{
			throw new GorgePasContentException(HttpStatus.NOT_FOUND, "This Concert id does not exists");
		}
		
		if(concert.getLocation() != null && !concert.getLocation().isBlank())
			concertToUpdate.setLocation(concert.getLocation());
		
		if(concert.getName() != null && !concert.getName().isBlank())
			concertToUpdate.setName(concert.getName());
		
		
		return concertsRepo.save(concertToUpdate);
	}

	@Operation(summary = "Get all concerts")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Concert> getConcerts() throws GorgePasContentException {
		return concertsRepo.findAll();
	}
	
	@Operation(summary = "Get today's concert")
	@RequestMapping(path = "/today", method = RequestMethod.GET)
	public Concert getTodaysConcert() throws GorgePasContentException {
	
	
		return concertsRepo.findByDate(getTodayDate());
	}
	
	@Operation(summary ="Register an IP in today's concert IP list, used to count eople in the audience")
	@RequestMapping(path = "/hello", method = RequestMethod.GET)
	public Concert addIp(HttpServletRequest request) throws GorgePasContentException {
	
		Concert concert = concertsRepo.findByDate(getTodayDate());
		
		if(concert == null) {
			concert = new Concert();
			concert.setIps(new HashSet<String>());
		}
		
		concert.getIps().add(request.getRemoteAddr());
			
	
		return concertsRepo.save(concert);
	}
	
	@Operation(summary = "Return number of people playing the game in today's audience")
	@RequestMapping(path = "/crowd", method = RequestMethod.GET)
	public Integer getIpNumber() throws GorgePasContentException {

		Concert concert = concertsRepo.findByDate(getTodayDate());
		if(concert == null)
			return 0;
		return concert.getIps().size();
	}
}
