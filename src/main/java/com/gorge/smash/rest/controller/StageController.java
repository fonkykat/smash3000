package com.gorge.smash.rest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.Stage;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.StageRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/stages")
public class StageController extends RestControllerBase
{

	@PostConstruct
	public void init()
	{
		LOGGER = LoggerFactory.getLogger(StageController.class);
	}

	@Autowired
	StageRepository stageRepo;

	@Operation(summary = "Add a new Chapter")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public Stage newStage(@RequestBody Stage stage) throws GorgePasContentException
	{
		if(stageRepo.existsByNumber(stage.getNumber()))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This stage number is already assigned");
		
		return stageRepo.save(stage);
	}
	
	@Operation(summary = "Add a Stage List")
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public List<Stage> addStageList(@RequestBody List<Stage> stages) throws GorgePasContentException
	{
		return stageRepo.saveAll(stages);
	}
		
	
	@Operation(summary = "Get all stages")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Stage> getAllStages() throws GorgePasContentException{

		return stageRepo.findAll();
		
	}
	
	@Operation(summary = "Get one stage  by its stage number")
	@RequestMapping(path = "/{number}", method = RequestMethod.GET)
	public Stage getChapByNumber(@PathVariable("number") Integer number) throws GorgePasContentException
	{
		Stage chap = stageRepo.findByNumber(number);
		if(chap == null)
			throw new GorgePasContentException(HttpStatus.NOT_FOUND, "This stage does not exists");
		
		return chap;

	}
}
