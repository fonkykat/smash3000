package com.gorge.smash.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter
{
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
	{
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Value("${migration.max_thread}")
	private Integer migrationMaxThread;

	@Bean(name = "exampleExecutor")
	public ThreadPoolTaskExecutor operationThreadPoolTaskExecutor()
	{
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(50);
		pool.setMaxPoolSize(50);
		pool.setAllowCoreThreadTimeOut(true);
		pool.setKeepAliveSeconds(120);
		return pool;
	}

}
