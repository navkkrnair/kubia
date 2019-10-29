package com.kubia;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KubiaApplication
{
	private static final Logger logger = LoggerFactory.getLogger(KubiaApplication.class);
	private int                 requestCount;

	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(KubiaApplication.class);
		app.addListeners((e) ->
		{
			if (e instanceof ApplicationReadyEvent)
			{
				File file = new File(File.separatorChar + "var" + File.separatorChar + "ready");
				logger.info("Creating file at: " + file);
				if (!(file.exists()))
				{
					try
					{
						file.createNewFile();
						logger.info("created file: " + file);
					}
					catch (IOException e1)
					{
						logger.info("Creating file failed");
					}

				}
			}
		});
		app.run(args);
	}

	@GetMapping("/")
	public Map<String, String> init() throws UnknownHostException, TooManyRequestsException
	{
		String hostname = InetAddress.getLocalHost().getHostName();
		logger.info("Executing version 3 from host {}", hostname);
		if (++requestCount >= 5)
			throw new TooManyRequestsException("Too many requests");
		return Collections.singletonMap("info", "This is v3 running from host: " + hostname);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, String> handleException(TooManyRequestsException e)
	{
		return Collections.singletonMap("error", e.getMessage());
	}

}
