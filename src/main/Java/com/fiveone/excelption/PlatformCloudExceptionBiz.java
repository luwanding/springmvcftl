package com.fiveone.excelption;

public class PlatformCloudExceptionBiz extends RuntimeException {

	private static final long serialVersionUID = 2677876771538620743L;
	
	private String code;

	public PlatformCloudExceptionBiz() {
	}
	
	public PlatformCloudExceptionBiz(String message) {
		super(message);
	}

	public PlatformCloudExceptionBiz(Throwable cause) {
		super(cause);
	}

	public PlatformCloudExceptionBiz(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PlatformCloudExceptionBiz(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
