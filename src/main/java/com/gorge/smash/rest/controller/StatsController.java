package com.gorge.smash.rest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.model.entity.Stats;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.StatsRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/stats")
public class StatsController extends RestControllerBase {

	@PostConstruct
	public void init() {
		LOGGER = LoggerFactory.getLogger(StatsController.class);
	}

	@Autowired
	StatsRepository statsRepo;

	@Operation(summary = "Get all stats")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Stats> getStats() throws GorgePasContentException {
		return statsRepo.findAll();
	}
	
	@Operation(summary = "Delete all Stats")
	@RequestMapping(path = "", method = RequestMethod.DELETE)
	public void deleteStats() throws GorgePasContentException {
		statsRepo.deleteAll();
	}
}
