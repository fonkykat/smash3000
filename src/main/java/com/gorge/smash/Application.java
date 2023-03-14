package com.gorge.smash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.gorge" })
// @EntityScan("com.gorge.app.model.entity")
// @EnableJpaRepositories("com.gorge.app.rest.repository")
public class Application
{
	public static void main(String[] args)
	{
		// Wait for container MySql server to start
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SpringApplication.run(Application.class, args);
	}
}
