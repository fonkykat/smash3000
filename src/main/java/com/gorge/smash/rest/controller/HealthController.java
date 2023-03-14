package com.gorge.smash.rest.controller;

import java.time.Duration;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gorge.smash.rest.exception.GorgePasContentException;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

@RestController
@RequestMapping("/health")
@CrossOrigin(originPatterns = "*")
public class HealthController
{
	protected static Logger LOGGER = null;

	private final Bucket bucket;

	public HealthController()
	{
		Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));
		this.bucket = Bucket4j.builder().addLimit(limit).build();
	}

	@RequestMapping(path = "", method = RequestMethod.GET)
	public ResponseEntity<String> healthCheck() throws GorgePasContentException
	{
		if (bucket.tryConsume(1))
		{
			return ResponseEntity.ok("OK");
		}

		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
	}
}
