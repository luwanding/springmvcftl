package com.fiveone.excelption;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 云平台异常基础类
 * @author xudelin
 *
 */

public class PlatformCloudException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Map<String,String>  EXCEPTION_TYPE = new HashMap<String,String>();
	
	static{
	     EXCEPTION_TYPE.put("PlatformCloudExceptionNotFund","NOT FUND");
	}
	
	private  String code;
	private  String type;
	
	public PlatformCloudException(String message, String code,String type){
		super(formatMessage( message,  code, type));
		this.code = code;
		this.type = type;
	}
	
	public PlatformCloudException(String message, String code, Throwable t ,String type){
		super(formatMessage( message,  code, type),t);
		this.code = code;
		this.type = type;
	}
	
	private static String getExceptionType(String exceptionName){
		return EXCEPTION_TYPE.get(exceptionName);
	}
	
	private static String formatMessage(String message, String code,String type){
		return MessageFormat.format("{0} >>> 错误类型：{1} 错误代码:{2} ",message, getExceptionType(type),code);
	}
	
	public  String getMessage(){
		return this.getMessage( MessageFormat.format("错误类型：{0} 错误代码:{1} 明细：{2}",type, code, this.getMessage(code) ));
	}
	
	private String getMessage(String code){
		return super.getMessage();
	}
}
