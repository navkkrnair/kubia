package com.kubia;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Map;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
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
	public Map<String, String> init() throws UnknownHostException
	{
		String hostname = InetAddress.getLocalHost().getHostName();
		logger.info("Executing init() from host {}", hostname);
		return Collections.singletonMap("hostname", hostname);
	}

	@GetMapping("/kubia")
	public Map<String, String> init2() throws UnknownHostException
	{
		String hostname = InetAddress.getLocalHost().getHostName();
		logger.info("Executing init2() through port 443 from host {}", hostname);
		return Collections.singletonMap("hostname", hostname + " through port 443");
	}

	@Bean
	public WebServerFactoryCustomizer containerCustomizer(@Value("${server.http.port}") int httpPort)
	{
		return new WebServerFactoryCustomizer()
		{
			@Override
			public void customize(WebServerFactory container)
			{
				if (container instanceof TomcatServletWebServerFactory)
				{
					TomcatServletWebServerFactory containerFactory = (TomcatServletWebServerFactory) container;

					Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
					connector.setPort(httpPort);
					containerFactory.addAdditionalTomcatConnectors(connector);
				}
			}
		};
	}
}
