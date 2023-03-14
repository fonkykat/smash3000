package com.gorge.smash.rest.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class GorgePasContentException extends Exception
{
	public HttpStatus status;

	public GorgePasContentException(HttpStatus _status, String msg)
	{
		super(msg);
		this.status = _status;
	}

	public HttpStatus getStatus()
	{
		return status;
	}

	public void setStatus(HttpStatus status)
	{
		this.status = status;
	}

}
