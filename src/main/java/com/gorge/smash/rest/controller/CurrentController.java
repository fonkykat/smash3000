package com.gorge.smash.rest.controller;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.model.entity.Current;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ChapterRepository;
import com.gorge.smash.rest.repository.CurrentRepository;
import com.gorge.smash.rest.repository.StatsRepository;
import com.gorge.smash.service.interf.StatsService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/current")
public class CurrentController extends RestControllerBase {

	@PostConstruct
	public void init() {
		LOGGER = LoggerFactory.getLogger(CurrentController.class);
	}

	@Autowired
	CurrentRepository currentRepo;

	@Autowired
	ChapterRepository chapterRepo;

	@Autowired
	StatsService statsService;

	@Operation(summary = "Set current chapter to {number}")
	@RequestMapping(path = "/{number}", method = RequestMethod.PUT)
	public Current setCurrent(@PathVariable("number") Integer number) throws GorgePasContentException {
		List<Current> currents = currentRepo.findAll();
		if (currents == null || currents.isEmpty()) {
			Current oups = new Current();
			oups.setId(0);
			oups.setNumber(0);
			currentRepo.save(oups);
			currents = Collections.<Current>emptyList();
			currents.add(oups);
		}

		Current current = currents.get(0);

		Integer previous = current.getNumber();

		if (previous > 0)
			statsService.saveStats(previous);

		if (chapterRepo.existsByNumber(number)) {
			current.setNumber(number);
			return currentRepo.save(current);
		}

		throw new GorgePasContentException(HttpStatus.CONFLICT, "Requested Chapter does not exist");
	}

	@Operation(summary = "Get current chapter number")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public Current getCurrent() throws GorgePasContentException {
		return currentRepo.getById(0);
	}
}
