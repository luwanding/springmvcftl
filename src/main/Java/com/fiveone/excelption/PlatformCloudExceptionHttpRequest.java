package com.fiveone.excelption;



/**
 * 云平台异常
 * 发送http post or get 请求时异常
 * @author xudelin
 *
 */
public class PlatformCloudExceptionHttpRequest  extends PlatformCloudException{
	
	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;
	
	private static final String classFlag = PlatformCloudExceptionHttpRequest.class.getName();
	
	public PlatformCloudExceptionHttpRequest(String message, String code) {
		super( message, code,classFlag);
	}
	
	public PlatformCloudExceptionHttpRequest(String message, String code, Throwable t) {
		super( message, code,t,classFlag);
	}
}
