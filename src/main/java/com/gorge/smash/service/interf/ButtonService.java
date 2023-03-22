package com.gorge.smash.service.interf;

import java.util.List;

import com.gorge.smash.model.entity.Button;
import com.gorge.smash.rest.exception.GorgePasContentException;

public interface ButtonService
{
	public void resetCounters() throws GorgePasContentException;
	 
	public List<Button> getAll() throws GorgePasContentException;
}
