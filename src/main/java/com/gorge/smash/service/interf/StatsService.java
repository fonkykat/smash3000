package com.gorge.smash.service.interf;

import com.gorge.smash.rest.exception.GorgePasContentException;

public interface StatsService
{
	public void saveStats(Integer chapterNumber) throws GorgePasContentException;
}
