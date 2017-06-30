package com.fiveone.excelption;

public class PlatformCloudExceptionGetHttpClient extends PlatformCloudException{
	
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	private static final String classFlag = PlatformCloudExceptionGetHttpClient.class.getName();
	
	public PlatformCloudExceptionGetHttpClient(String message, String code) {
		super( message, code,classFlag);
	}
	
	public PlatformCloudExceptionGetHttpClient(String message, String code, Throwable t) {
		super( message, code,t,classFlag);
	}
}
