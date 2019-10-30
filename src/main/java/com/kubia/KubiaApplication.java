package com.kubia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication

public class KubiaApplication
{
	private static final Logger logger = LoggerFactory.getLogger(KubiaApplication.class);

	public static void main(String[] args)
	{
		SpringApplication.run(KubiaApplication.class, args);
	}

	@Bean
	CommandLineRunner initFile()
	{
		return args ->
		{
			Path path = Paths.get(Constants.DATA_FILE);
			try
			{
				Files.createFile(path);
				logger.info("File created");
			}
			catch (IOException e)
			{
				logger.error("File already exists:" + e.getMessage());
			}
		};
	}

	@Bean
	CommandLineRunner initDns()
	{
		return args ->
		{
			Hashtable<String, String> env = new Hashtable<>();
			env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			env.put("java.naming.provider.url", "dns:");
			DirContext ctx   = new InitialDirContext(env);
			Attributes attrs = ctx.getAttributes(Constants.SERVICE_NAME, new String[]
			{ "SRV" });
			NamingEnumeration en = attrs.getAll();
			while (en.hasMoreElements())
			{
				Object object = (Object) en.nextElement();
				System.out.println(object);
				
			}
		};
	}

}
