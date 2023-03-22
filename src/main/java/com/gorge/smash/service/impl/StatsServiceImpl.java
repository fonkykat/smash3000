package com.gorge.smash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.model.entity.Chapter;
import com.gorge.smash.model.entity.Stats;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ChapterRepository;
import com.gorge.smash.rest.repository.StatsRepository;
import com.gorge.smash.service.interf.ButtonService;
import com.gorge.smash.service.interf.StatsService;

@Service
public class StatsServiceImpl implements StatsService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(StatsServiceImpl.class);

	@Autowired
	ButtonService buttonService;
	
	@Autowired
	StatsRepository statsRepo;
	
	@Autowired
	ChapterRepository chapterRepo;

	@Override
	public void saveStats(Integer chapterNumber) throws GorgePasContentException
	{
		Chapter chap = chapterRepo.findByNumber(chapterNumber);
		
		if(chap == null)
			throw new GorgePasContentException(HttpStatus.INTERNAL_SERVER_ERROR, "Previous CHapter does'nt exist");
		
		List<Button> buttons = buttonService.getAll();
		
		Stats stats = new Stats();
		
		stats.setChapter(chap);
		for(Button b : buttons) {
			switch(b.getName()) {
			case "a":
				stats.setA(b.getCount());
			case "b":
				stats.setB(b.getCount());
			case "x":
				stats.setX(b.getCount());
			case "y":
				stats.setY(b.getCount());
			}
		}
		
		stats = statsRepo.save(stats);
		
		buttonService.resetCounters();
	}

}
