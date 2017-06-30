package com.fiveone.util;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

/**
 * HTTP 请求结果
 * @author xudelin
 *
 */
public class HttpResult {

	private short responseCode = 0;
	private String location = "";
	private String responseBody;
	private byte[] responseByte;
	private Header[] responseHeads;
	private boolean reLogin = false;
	

	public boolean isReLogin() {
		return reLogin;
	}

	public void setReLogin(boolean reLogin) {
		this.reLogin = reLogin;
	}

	public byte[] getResponseByte() {
		return responseByte;
	}
	
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public void setResponseByte(byte[] responseByte) {
		this.responseByte = responseByte;
	}

	public short getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(short responseCode) {
		this.responseCode = responseCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getResponseBody() {
		if (responseBody == null)
			try {
				responseBody = new String(responseByte, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return responseBody;
	}

	public Header[] getResponseHeads() {
		return responseHeads;
	}

	public void setResponseHeads(Header[] responseHeads) {
		System.out.println("start out headers........");
		for(int i = 0; i < responseHeads.length; i ++){
			System.out.println("name:"+responseHeads[i].getName() + "    value:"+ responseHeads[i].getValue());
		}
		System.out.println("end out headers........");
		this.responseHeads = responseHeads;
	}
}
