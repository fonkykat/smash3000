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
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ChapterRepository;

@RestController
@RequestMapping("/chapters")
public class ChapterController extends RestControllerBase
{

	@PostConstruct
	public void init()
	{
		LOGGER = LoggerFactory.getLogger(ChapterController.class);
	}

	@Autowired
	ChapterRepository chapterRepo;

	@RequestMapping(path = "", method = RequestMethod.POST)
	public Chapter newChapter(@RequestBody Chapter chapter) throws GorgePasContentException
	{
		if(chapterRepo.existsByNumber(chapter.getNumber()))
			throw new GorgePasContentException(HttpStatus.CONFLICT, "This chapter number is already assigned");
		
		return chapterRepo.save(chapter);
	}
	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<Chapter> getAllChapters() throws GorgePasContentException{

		return chapterRepo.findAll();
		
	}
	
	
	@RequestMapping(path = "/{number}", method = RequestMethod.GET)
	public Chapter getChapByNumber(@PathVariable("number") Integer number) throws GorgePasContentException
	{
		Chapter chap = chapterRepo.findByNumber(number);
		if(chap == null)
			throw new GorgePasContentException(HttpStatus.NOT_FOUND, "This chapter does not exists");
		
		return chap;

	}
}
