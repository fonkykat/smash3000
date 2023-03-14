package com.gorge.smash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ButtonRepository;
import com.gorge.smash.service.interf.ButtonService;

@Service
public class ButtonServiceImpl implements ButtonService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ButtonServiceImpl.class);

	@Autowired
	ButtonRepository buttonRepo;

	@Override
	public void resetCounters() throws GorgePasContentException
	{
		List<Button> buttons = buttonRepo.findAll();

		buttons.stream().forEach(b -> {
			b.setCount(0);
			buttonRepo.save(b);
		});

		return;
	}

}
