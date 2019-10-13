package com.kubia.health;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator
{
	public boolean isHealthy = true;

	public CustomHealthIndicator()
	{
		ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
		scheduledService.schedule(() ->
		{ isHealthy = false; }, 30, TimeUnit.SECONDS);
	}

	@Override
	public Health health()
	{

		return isHealthy ? Health.up().build() : Health.down().build();
	}

}
