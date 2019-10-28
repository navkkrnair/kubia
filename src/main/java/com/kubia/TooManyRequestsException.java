package com.kubia;

public class TooManyRequestsException extends Exception
{
	public TooManyRequestsException(String cause)
	{
		super(cause);
	}

}
