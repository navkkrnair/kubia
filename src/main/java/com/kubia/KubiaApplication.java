package com.kubia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/kubia")
public class KubiaApplication
{
	private static final Logger logger = LoggerFactory.getLogger(KubiaApplication.class);
	// For windows use "D:" + File.separatorChar + "var" + File.separatorChar + "data" + File.separatorChar	+ "kubia.txt";
	private static final String dataFile = File.separatorChar + "var" + File.separatorChar + "data" + File.separatorChar
			+ "kubia.txt";

	public static void main(String[] args)
	{
		Path path = Paths.get(dataFile);
		try
		{
			Files.createFile(path);
			logger.info("File created");
		}
		catch (IOException e)
		{
			logger.error("File already exists:" + e.getMessage());
		}
		SpringApplication.run(KubiaApplication.class, args);
	}

	@GetMapping
	public String get() throws UnknownHostException
	{
		String hostname = InetAddress.getLocalHost().getHostName();
		logger.info("Executing get() from host {}", hostname);

		Path           path  = Paths.get(dataFile);
		Stream<String> lines = null;
		try
		{
			lines = Files.lines(path);
		}
		catch (IOException e)
		{
			logger.info(">> Exception reading file: {}", e.getMessage());
		}
		String data = lines.collect(Collectors.joining("\n"));
		lines.close();
		return "Data stored on this pod " + hostname + ", is " + data;
	}

	@PostMapping
	public ResponseEntity<?> post(@RequestBody String data) throws UnknownHostException
	{
		String hostname = InetAddress.getLocalHost().getHostName();
		logger.info("Executing init() from host {}", hostname);

		File        file = new File(Paths.get(dataFile).toString());
		PrintWriter out  = null;
		try
		{
			out = new PrintWriter(new FileWriter(file, true));
		}
		catch (IOException e)
		{
			logger.info(">> Exception writing to file: {}", e.getMessage());
		}
		out.append(data);
		out.close();

		return ResponseEntity.ok().build();
	}

}
