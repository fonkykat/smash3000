package com.gorge.smash.ws;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gorge.smash.ws.error.RxErrorHandlingCallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class WSHelper
{
	private WSHelper()
	{
		throw new IllegalStateException("WSHelper is an utility class");
	}

	public static Builder defaultBuilder(String baseUrl, OkHttpClient.Builder clientBuilder)
	{
		HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
		clientBuilder.addInterceptor(logging);
		clientBuilder.readTimeout(40, TimeUnit.SECONDS);
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		return new Retrofit.Builder().baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create(objectMapper))
				.addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
				.client(clientBuilder.build());
	}

}
