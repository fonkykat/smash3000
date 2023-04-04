package com.gorge.smash.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.model.entity.ButtonPress;
import com.gorge.smash.rest.exception.GorgePasContentException;
import com.gorge.smash.rest.repository.ButtonPressRepository;
import com.gorge.smash.rest.repository.ButtonRepository;
import com.gorge.smash.service.interf.ButtonService;
import com.gorge.smash.util.TempoError;

@Service
public class ButtonServiceImpl implements ButtonService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ButtonServiceImpl.class);

	@Autowired
	ButtonRepository buttonRepo;
	
	@Autowired
	ButtonPressRepository buttonPressRepo;

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

	@Override
	public List<Button> getAll() throws GorgePasContentException
	{
		
		return buttonRepo.findAll();
	}

	@Override
	public TempoError getPublicError(String name) throws GorgePasContentException {
		
		ButtonPress lastAdmin = buttonPressRepo.findFirstByNameOrderByTimestampDesc("admin");
		
		if(lastAdmin == null) return new TempoError((long)-1 , (double)-1, (double)-1);
		
		List<ButtonPress> crowdInput = buttonPressRepo.findByNameAndTimestampBetween(name, lastAdmin.getTimestamp() - 300, lastAdmin.getTimestamp() + 300);
		Double crowdAverage = crowdInput.stream().mapToLong(x -> x.getTimestamp()).average().orElse(-1);
		
		if(crowdAverage == -1)
			return new TempoError(lastAdmin.getTimestamp(), (double)-1, (double)-1);
		
		return new TempoError(lastAdmin.getTimestamp(), crowdAverage, lastAdmin.getTimestamp() - crowdAverage);
		}

}
