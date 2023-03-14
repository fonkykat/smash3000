package com.gorge.smash.rest.controller;

import org.slf4j.Logger;

public class RestControllerBase
{
	protected static Logger LOGGER = null;

	/**
	 * Print operation type banner
	 * 
	 * @param title
	 */
	private void printBanner(String title)
	{
		title = String.format("-------->    %s", title);
		LOGGER.info("================================");
		LOGGER.info(title);
		LOGGER.info("================================");
	}
}
