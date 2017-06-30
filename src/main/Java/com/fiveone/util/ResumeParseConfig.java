package com.fiveone.util;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class ResumeParseConfig {
	
	public static final String TESSERACT_IMAGECRACK_PATH = "TESSERACT_IMAGECRACK_PATH";
	public static final String CHARSET = "CHARSET";
	public static final String AGENTOPTION = "AGENT_OPTION_";
	
	public static final String JOB51_LOGIN_URL = "JOB51_LOGIN_URL";
	public static final String JOB51_MAINLOGIN_URL = "JOB51_MAINLOGIN_URL";
	public static final String JOB51_BASE_URL = "JOB51_BASE_URL";
	public static final String JOB51_RESUME_URL = "JOB51_RESUME_URL";
	public static final String JOB51_SEARCH_URL = "JOB51_SEARCH_URL";
	public static final String JOB51_RESUME_COUNT_LABEL = "JOB51_RESUME_COUNT_LABEL";
	public static final String JOB51_RESUME_PAGESIZE_LABEL = "JOB51_RESUME_PAGESIZE_LABEL";
	public static final String JOB51_RESUME_ENUME_LABEL = "JOB51_RESUME_ENUME_LABEL";
	public static final String JOB51_RESUME_INBOXBASE_URL = "JOB51_RESUME_INBOXBASE_URL";
	public static final String JOB51_RESUME_INBOX_URL = "JOB51_RESUME_INBOX_URL";
	public static final String JOB51_RESUME_INBOX_PAGECOUNT_LABEL = "JOB51_RESUME_INBOX_PAGECOUNT_LABEL";
	public static final String JOB51_RESUME_INBOX_PAGESIZE_LABEL = "JOB51_RESUME_INBOX_PAGESIZE_LABEL";
	public static final String JOB51_RESUME_INBOX_FORM_LABEL = "JOB51_RESUME_INBOX_FORM_LABEL";
	public static final String JOB51_RESUME_INBOX_PAGENUM_LABEL = "JOB51_RESUME_INBOX_PAGENUM_LABEL";
	public static final String JOB51_RESUME_INBOX_PAGETOP_LABEL = "JOB51_RESUME_INBOX_PAGETOP_LABEL";
	public static final String JOB51_RESUME_INBOX_PAGETOPSIZE_LABEL = "JOB51_RESUME_INBOX_PAGETOPSIZE_LABEL";
	public static final String JOB51_RESUME_INBOX_COUNT_LABEL = "JOB51_RESUME_INBOX_COUNT_LABEL";
	public static final String JOB51_RESUME_INBOX_SELECT_LABEL = "JOB51_RESUME_INBOX_SELECT_LABEL";
	public static final String JOB51_RESUME_INBOX_SELECT_SENDDATE = "JOB51_RESUME_INBOX_SELECT_SENDDATE";
	public static final String JOB51_RESUME_INBOX_SELECT_SENDDATE_IDX = "JOB51_RESUME_INBOX_SELECT_SENDDATE_IDX";
	public static final String JOB51_HOST_URL = "JOB51_HOST_URL";
	public static final String JOB51_LOGIN_HTTPS_URL = "JOB51_LOGIN_HTTPS_URL";
	public static final String JOB51_ORIGN_URL = "JOB51_ORIGN_URL";
	public static final String JOB51_MEMBER_URL = "JOB51_MEMBER_URL";
	
	public static final String JOB51_LOGIN_TEST_URL = "JOB51_LOGIN_TEST_URL";//登录情况测试地址
	public static final String JOB51_LOGIN_REDO_FLAG = "JOB51_LOGIN_REDO_FLAG";//重新登录重定向地址标志
	
	public static final String JOB51_RESUME_DOWLOAD_URL = "JOB51_RESUME_DOWLOAD_URL";//下载简历URL
	
	
	public static final String LIEPIN_AJAXLOGIN_URL = "LIEPIN_AJAXLOGIN_URL";
	public static final String LIEPIN_RESULT_LABEL = "LIEPIN_RESULT_LABEL";
	public static final String LIEPIN_MAIN_URL = "LIEPIN_MAIN_URL";
	public static final String LIEPIN_WORKEXPERIENCE_URL = "LIEPIN_WORKEXPERIENCE_URL";
	public static final String LIEPIN_LOGIN_PARAM = "LIEPIN_LOGIN_PARAM_";
	public static final String LIEPIN_RESUME_DETAIL_URL = "LIEPIN_RESUME_DETAIL_URL";
	public static final String LIEPIN_RESUME_IMAGE11_URL = "LIEPIN_RESUME_IMAGE11_URL";
	public static final String LIEPIN_RESUME_IMAGE00_URL = "LIEPIN_RESUME_IMAGE00_URL";
	public static final String LIEPIN_RESUME_IMAGE00_FULLURL = "LIEPIN_RESUME_IMAGE00_FULLURL";
	public static final String LIEPIN_RESUME_IMAGE11_FULLURL = "LIEPIN_RESUME_IMAGE11_FULLURL";
	
	public static final String ZHILIAN_CHARSET = "ZHILIAN_CHARSET";
	public static final String ZHILIAN_SEARCH_URL = "ZHILIAN_SEARCH_URL";
	public static final String ZHILIAN_LOGIN_URL = "ZHILIAN_LOGIN_URL";
	public static final String ZHILIAN_POSITION_URL = "ZHILIAN_POSITION_URL";
	public static final String ZHILIAN_MYPOSITION_URL = "ZHILIAN_MYPOSITION_URL";
	public static final String ZHILIAN_FAVORITE_RESUME_URL = "ZHILIAN_FAVORITE_RESUME_URL";
	public static final String ZHILIAN_RESUME_DETAIL_URL = "ZHILIAN_RESUME_DETAIL_URL";
	public static final String ZHILIAN_LOGIN_TEST_URL = "ZHILIAN_LOGIN_TEST_URL";//登录情况测试地址
	public static final String ZHILIAN_LOGIN_REDO_FLAG = "ZHILIAN_LOGIN_REDO_FLAG";//重新登录重定向地址标志
	public static final String ZHILIAN_LOGIN_REDO_FLAG2 = "ZHILIAN_LOGIN_REDO_FLAG2";//重新登录重定向地址标志
	public static final String ZHILIAN_RESUME_DOWLOAD_URL = "ZHILIAN_RESUME_DOWLOAD_URL";//简历下载地址
	public static final String ZHILIAN_RESUME_ORIGIN_URL = "ZHILIAN_RESUME_ORIGIN_URL";//简历下载origin
	
	public static final String ZHILIAN_HEAD_PIC = "ZHILIAN_HEAD_PIC";//获取头像
	
	
	public static final String ZHUOPIN_LOGIN_URL = "ZHUOPIN_LOGIN_URL";
	public static final String ZHUOPIN_MAIN_URL = "ZHUOPIN_MAIN_URL";
	public static final String ZHUOPIN_VALIDCODE_URL = "ZHUOPIN_VALIDCODE_URL";
	public static final String ZHUOPIN_CHARSET = "ZHUOPIN_CHARSET";
	public static final String ZHUOPIN_RESUME_URL = "ZHUOPIN_RESUME_URL";
	public static final String ZHUOPIN_TOTALPAGE_PATH = "ZHUOPIN_TOTALPAGE_PATH";
	public static final String ZHUOPIN_EVERYPAGE_ELEMENT_LABEL = "ZHUOPIN_EVERYPAGE_ELEMENT_LABEL";
	public static final String ZHUOPIN_BASE_URL = "ZHUOPIN_BASE_URL";
	public static final String ZHUOPIN_SHOWCONTACT_URL = "ZHUOPIN_SHOWCONTACT_URL";
	public static final String ZHUOPIN_REPLACE_URL = "ZHUOPIN_REPLACE_URL";
	public static final String ZHUOPIN_INDEXOF_URL = "ZHUOPIN_INDEXOF_URL";
	
	//简历头像存储路径
	public static final String HEAD_IMAGE_STORE_PATH = "HEAD_IMAGE_STORE_PATH";
	
	//错误简历格式存放路径
	public static final String RESUME_ERROR_STORE_PATH = "RESUME_ERROR_STORE_PATH";
	
	
	
	
	private static PropertiesConfiguration config;
	
	static {
		try {
			String path = System.getenv("resources.config.path");
			if(path == null)
				path = System.getProperty("resources.config.path");
			config = new PropertiesConfiguration(path +File.separator+"hrmanage"+ File.separator + "configuration.properties");
//			config = new PropertiesConfiguration("configuration.properties");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取配置参数值，先从Map缓存中获取若没有则从配置文件中获取
	 * @param propertyName
	 * @return
	 */
	 public static String getValue(String propertyName){
		 String value = config.getString(propertyName);
		 return value;
	 }

	 /**
	  * 获取猎聘的登录参数
	  * @return
	  */
	public static List<NameValuePair> getLiepinLoginParam(){
		List<NameValuePair> loginParames = new ArrayList<NameValuePair>();
		String nameValueOptionValue;
		for (int i = 1; i < 3; i++) {
			try {
				nameValueOptionValue = getValue(LIEPIN_LOGIN_PARAM + i);
			} catch (MissingResourceException e) {
				nameValueOptionValue = null;
			}
			if (nameValueOptionValue != null
					&& nameValueOptionValue.indexOf(":") > 0) {
				String keyValue[] = nameValueOptionValue.split(":");
				loginParames.add(new BasicNameValuePair(keyValue[0],
						keyValue[1]));
			}
		}
		return loginParames;
	}

}
