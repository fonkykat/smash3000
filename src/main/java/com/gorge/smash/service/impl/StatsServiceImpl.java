package com.gorge.smash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.model.entity.Stage;
import com.gorge.smash.model.entity.Stats;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.StageRepository;
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
	StageRepository stageRepo;

	@Override
	public void saveStats(Integer chapterNumber) throws GorgePasContentException
	{
		Stage chap = stageRepo.findByNumber(chapterNumber);
		
		if(chap == null)
			throw new GorgePasContentException(HttpStatus.INTERNAL_SERVER_ERROR, "Previous CHapter does'nt exist");
		
		List<Button> buttons = buttonService.getAll();
		
		Stats stats = new Stats();
		
		stats.setChapter_number(chapterNumber);
		for(Button button : buttons) {
			switch(button.getName()) {
			case "a":
				stats.setA(button.getCount());
			case "b":
				stats.setB(button.getCount());
			case "x":
				stats.setX(button.getCount());
			case "y":
				stats.setY(button.getCount());
			case "shake":
				stats.setSHAKE(button.getCount());
			}
		}
		
		stats = statsRepo.save(stats);
		
		buttonService.resetCounters();
	}

}
