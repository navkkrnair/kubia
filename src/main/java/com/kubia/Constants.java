package com.kubia;

import java.io.File;

public class Constants
{
	//To test in windows, use "D:" + File.separatorChar + "var" + File.separatorChar + "data" + File.separatorChar	+ "kubia.txt"; 
	/*public static final String DATA_FILE = "D:" + File.separatorChar + "var" + File.separatorChar + "data" + File.separatorChar
			+ "kubia.txt";*/

	// To use in Docker/ Kubernetes 
	public static final String DATA_FILE = File.separatorChar + "var" + File.separatorChar + "data" + File.separatorChar
			+ "kubia.txt";

	public static final String SERVICE_NAME = "kubia-headless-service.default.svc.cluster.local";

}
