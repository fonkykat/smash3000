package com.gorge.smash.rest.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(path = "/{number}", method = RequestMethod.PUT)
	public Chapter incrChapter(@PathVariable("number") Integer number) throws GorgePasContentException
	{
		List<Chapter> chaps = chapterRepo.findAll();
		Chapter chap = chaps.get(0);
		chap.setNumber(number);
		return chapterRepo.save(chap);
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public Chapter getChap() throws GorgePasContentException
	{
		List<Chapter> chaps = chapterRepo.findAll();
		return chaps.get(0);

	}
}
