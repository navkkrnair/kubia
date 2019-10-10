package com.k8s;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KubiaApplication
{
	private static final Logger logger = LoggerFactory.getLogger(KubiaApplication.class);

	public static void main(String[] args)
	{
		SpringApplication.run(KubiaApplication.class, args);
	}

	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.OK)
	public Map<String, String> init() throws UnknownHostException
	{
		logger.info("Executing init()");
		return Collections.singletonMap("host", InetAddress.getLocalHost().getHostName());
	}

}
