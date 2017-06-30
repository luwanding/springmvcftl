package com.fiveone.excelption;

/**
 * 云平台异常无法找到异常
 * @author xudelin
 *
 */
public class PlatformCloudExceptionNotFund extends PlatformCloudException{
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	private static final String classFlag = PlatformCloudExceptionNotFund.class.getName();
	
	public PlatformCloudExceptionNotFund(String message, String code) {
		super( message, code,classFlag);
	}
	
	public PlatformCloudExceptionNotFund(String message, String code, Throwable t) {
		super( message, code,t,classFlag);
	}

}
