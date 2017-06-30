package com.fiveone.util;

import com.fiveone.excelption.PlatformCloudExceptionGetHttpClient;
import com.fiveone.excelption.PlatformCloudExceptionHttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTTP客户端工具类， 包括分发HTTP请求到代理服务器。
 * 
 * @author xudelin
 */
public class HttpClient implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final short HTTP_STATUS_CODE_301 = 301;
	public static final short HTTP_STATUS_CODE_403 = 403;
	public static final short HTTP_STATUS_CODE_302 = 302;
	private static final String UC_AGENT_FLAG = "User-Agent";
	private static final int SOCK_TIMEOUT = 10000;
	private static final int CONN_TIMEOUT = 10000;
	private static Logger Log = Logger.getLogger(HttpClient.class);
	private static final List<String> AGENT_FLAG = new ArrayList<String>();
	private static final List<HttpHost> SERVER_IP = new ArrayList<HttpHost>();
	private  List<Cookie> cookieList = new ArrayList<Cookie>();
	private static final int TIMEOUT_SLEEP_TIME = 1000;//超时等待时间
//	private volatile static  int PROXY_INDEX = 0;//超时等待时间
//	private volatile static int REQUEST_SIZE = 0;
//	private static int REQUEST_MAX_SIZE = 1;
	private CloseableHttpClient client;
	
	static {
		Properties pp = new Properties();
		try {
			pp.load(HttpClient.class.getResourceAsStream("/webProxyClient.properties"));
			// 加载多个Client agent 用于
			String agent = pp.getProperty("uc_agent",
							"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36");
			String[] agents = agent.split("&&");
			for (int i = 0; i < agents.length; i++) {
				AGENT_FLAG.add(agents[i]);
			}
			
			String server = pp.getProperty("proxy_server", "localhost:9999");
			String[] servers = server.split("&&");
			for (int i = 0; i < servers.length; i++) {
				String[] serverAddress = servers[i].split(":");
				System.out.println(serverAddress[0]);
				HttpHost inet = new HttpHost(serverAddress[0], Integer.parseInt(serverAddress[1]));
				SERVER_IP.add(inet);
			}
		} catch (IOException e) {
			Log.error("加载HTTPClient参数文件[./webProxyClient.properties]异常", e);
		}
	}
	
	public HttpClient(CloseableHttpClient client){
		this.client = client;
	}
	
	public HttpClient() {
		
	}
	
	private static final Random r = new Random(SERVER_IP.size());
	
	public HttpResult doPost(List<NameValuePair> parames, String encode,
							 String url, Map<String, String> headerMap, String channel)
			throws PlatformCloudExceptionGetHttpClient,
			PlatformCloudExceptionHttpRequest {
		return this.doPost(parames, encode, url, headerMap, channel, true);
	}

	public HttpResult doGet(String url, Map<String, String> headerMap,
			String channel) throws PlatformCloudExceptionGetHttpClient,
			PlatformCloudExceptionHttpRequest {
		return this.doGet(url, headerMap, channel, true);
	}
	
	public boolean doGet_download(String filePath, String url, String channel) {
		return this.download(filePath, url, null, channel);
	}
	
	/**
	 * 发起post 请求 
	 * @param client
	 * @param parames
	 * @param encode
	 * @param url
	 * @param headerMap
	 * @return
	 * @throws PlatformCloudExceptionHttpClient 
	 * @throws PlatformCloudExceptionHttpRequest 
	 */
	public HttpResult doPost(List<NameValuePair> parames, String encode, String url,
							 Map<String, String> headerMap, String channel, boolean isproxy) throws PlatformCloudExceptionGetHttpClient, PlatformCloudExceptionHttpRequest {
		CloseableHttpResponse response = null;
		CloseableHttpClient client = null;
		HttpEntity resp_entity = null;
		HttpHost proxy = getProxyServer();
		if(!isproxy){
			proxy = null;
		}
		String error_msg = "调用 doGet 方法异常:\r\n 所用代理为：{0} \r\n 请求参数:[url:{1}  \r\n http_head:{2} \r\n timestamp:{3}";
		Log.debug(MessageFormat.format("开始调用 http post 方法 所用代理为：{0} \r\n 请求参数:[url:{1} \r\n parames:{2} \r\n encode:{3} \r\n http_head:{4} \r\n timestamp:{5} ",
						proxy, url, parames, encode, headerMap, System.currentTimeMillis()));
		try {
			HttpPost httppost = new HttpPost(url);
			if(parames != null){
				UrlEncodedFormEntity entity = null;
				if (StringUtils.isBlank(encode)) {
					entity = new UrlEncodedFormEntity(parames);
				} else {
					entity = new UrlEncodedFormEntity(parames, encode);
				}
				httppost.setEntity(entity);
			}
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResult result = httpRequest( response,  client,  httppost, resp_entity, channel,  url,  proxy);
			Log.debug("doPost 调用完成准备返回结果, timestamp:" + System.currentTimeMillis());
			return result;
		}catch (IOException e) {
			Log.error(MessageFormat.format(error_msg + "timeout 正准备重试...",
					proxy, url, headerMap, System.currentTimeMillis()), e);
			try {
				Thread.sleep(TIMEOUT_SLEEP_TIME);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return doPost( parames,  encode,  url,headerMap, channel,  isproxy);
		} catch (Exception e) {
			Log.warn(MessageFormat.format(error_msg,
					proxy, url, headerMap, System.currentTimeMillis()), e);
			throw new PlatformCloudExceptionHttpRequest("发送http post 请求时发生异常：","100004",e);
		} finally {
			release(response, resp_entity, client);
		}
	}
    
    /**
     * 发起get 请求
	 * @param client
	 * @param
	 * @param url
	 * @param headerMap
	 * @return
	 * @throws PlatformCloudExceptionGetHttpClient 无法获得httpClient实例异常
	 * @throws PlatformCloudExceptionHttpRequest 发送get或者post请求异常
	 */
	public HttpResult doGet(String url, Map<String, String> headerMap,
			String channel, boolean isproxy)
			throws PlatformCloudExceptionGetHttpClient,
			PlatformCloudExceptionHttpRequest {
		CloseableHttpResponse response = null;
		HttpEntity resp_entity = null;
		HttpHost proxy = getProxyServer();
		if (!isproxy) {
			proxy = null;
		}
		CloseableHttpClient client = null;
		Log.debug(MessageFormat.format("开始调用 http get 方法 所用代理为：{0} \r\n 请求参数:[url:{1}  \r\n http_head:{2} \r\n timestamp:{3}",
						proxy, url, headerMap, System.currentTimeMillis()));
		String error_msg = "调用 doGet 方法异常:\r\n 所用代理为：{0} \r\n 请求参数:[url:{1}  \r\n http_head:{2} \r\n timestamp:{3}";
		try {
			HttpGet httpget = new HttpGet(url);
			if (headerMap != null) {
				for (Map.Entry<String, String> entry : headerMap.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResult result = httpRequest(response, client, httpget, resp_entity, channel, url, proxy);
			Log.debug("doGet 调用完成准备返回结果, timstamp:" + System.currentTimeMillis());
			return result;
		} catch (IOException e) {
			Log.error(MessageFormat.format(error_msg + "  调用出现timeout 正在准备重试！",proxy,
					url, headerMap, System.currentTimeMillis()), e);
			try {
				Thread.sleep(TIMEOUT_SLEEP_TIME);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return doGet( url,  headerMap, channel,  isproxy);
		} catch (Exception e) {
			Log.error(MessageFormat.format(error_msg, proxy, url, headerMap, System.currentTimeMillis()), e);
			throw new PlatformCloudExceptionHttpRequest("发送http get 请求时发生异常：", "100004", e);
		} finally {
			release(response, resp_entity, client);
		}
	}
	
	private static final int SIZE = 1024;
	
	public boolean download(String filePath, String url,
			Map<String, String> headerMap, String channel) {
		boolean result = false;
		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		try {
			Log.debug(MessageFormat.format("开始下载文件, filePath:{0}, url:{1}, channel:{2}", filePath, url, channel));
			String key = channel + "_" + getHost(url);
			CloseableHttpClient client = HttpConnectionManager.getHttpClient(key,getCookieStore());
			HttpHost proxy = getProxyServer();
			HttpGet httpget = new HttpGet(url);
			RequestConfig config = RequestConfig.custom()
					.setSocketTimeout(SOCK_TIMEOUT)
					.setConnectTimeout(CONN_TIMEOUT)
					.setConnectionRequestTimeout(CONN_TIMEOUT).setProxy(proxy)
					.build();
			httpget.setConfig(config);
			httpget.setHeader("Accept-Encoding", "gzip, deflate"); 
			response = client.execute(httpget);
			entity = response.getEntity();
			if (entity != null) {
				String pfilePath = filePath.substring(0, filePath.lastIndexOf(File.separatorChar) + 1);
				Log.info("文件目录:" + pfilePath);
				File pfile = new File(pfilePath);
				if (!pfile.exists())
					pfile.mkdirs();
				InputStream is = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(is);
				OutputStream os = new FileOutputStream(filePath);
				BufferedOutputStream bos = new BufferedOutputStream(os);
				try {
					byte[] data = new byte[SIZE];
					int len = 0;
					while((len = bis.read(data)) != -1) {
						bos.write(data, 0, len);
					}
				} catch (Exception e) {
					httpget.abort();
					throw e;
				} finally {
					bos.flush();
					is.close();
					bis.close();
					os.close();
					bos.close();
				}
			}
			result = true;
		} catch (Exception e) {
			Log.error(MessageFormat.format("下载文件时出现异常, filePath:{0}, url:{1}, channel:{2}", filePath, url, channel), e);
		} finally {
			release(response, entity, client);
		}
		return result;
	}
	
	private HttpResult httpRequest(CloseableHttpResponse response,
								   CloseableHttpClient client, HttpRequestBase request,
								   HttpEntity resp_entity, String channel, String url, HttpHost proxy)
			throws ClientProtocolException, IOException,
			PlatformCloudExceptionGetHttpClient {
		url = java.net.URLEncoder.encode(url, "UTF-8");
		String key = channel + "_" + getHost(url);
		if (this.client != null) {
			client = this.client;
		} else {
			client = HttpConnectionManager.getHttpClient(key, getCookieStore());
		}
		long startTime = System.currentTimeMillis();
		RequestConfig config = RequestConfig.custom()
				.setSocketTimeout(SOCK_TIMEOUT)
				.setConnectTimeout(CONN_TIMEOUT)
				.setConnectionRequestTimeout(CONN_TIMEOUT)
				.setProxy(proxy)
				.build();
		
		request.setConfig(config);
		request.setHeader("Accept", "*/*");
		request.setHeader("Accept-Encoding", "gzip, deflate, sdch"); 
		request.setHeader(UC_AGENT_FLAG, getUC_Agent());
		
		HttpClientContext httpContext = HttpClientContext.create();
		httpContext.setCookieStore(getCookieStore());
		response = client.execute(request,httpContext);
		
		cookieList =  httpContext.getCookieStore().getCookies();
		HttpResult result = new HttpResult();
		resp_entity = response.getEntity();
		result.setResponseByte(EntityUtils.toByteArray(resp_entity));
		result.setResponseHeads(response.getAllHeaders());
		result.setResponseCode((short) response.getStatusLine().getStatusCode());
		String htmlSrc = new String(result.getResponseByte());
		/*if(htmlSrc.indexOf(ResumeParseConfig.getValue(ResumeParseConfig.ZHILIAN_LOGIN_REDO_FLAG2)) > 0){
			result.setReLogin(true);//需要重新登录
			Log.error("智联招聘重新登录, timestamp:" + System.currentTimeMillis() + ", html content:" + htmlSrc + ", url:" + url);
		}*/
		
		if (HTTP_STATUS_CODE_403 == result.getResponseCode()) {
			Log.error("重新登录, timestamp:" + System.currentTimeMillis() + ", html content:" + htmlSrc + ", url:" + url);
			result.setReLogin(true);// 需要重新登录
		} else if (HTTP_STATUS_CODE_301 == result.getResponseCode()
				|| HTTP_STATUS_CODE_302 == result.getResponseCode()) {
			String location = response.getHeaders("Location")[0].getValue();
			if (location.length() > 0) {
				result.setLocation(location);
				if (location.indexOf(ResumeParseConfig.getValue(ResumeParseConfig.JOB51_LOGIN_REDO_FLAG)) >= 0
							|| location.indexOf(ResumeParseConfig.getValue(ResumeParseConfig.ZHILIAN_LOGIN_REDO_FLAG)) >= 0) {
					result.setReLogin(true);// 需要重新登录
				}
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println(MessageFormat.format("**********************************\r\nURL:{0} 耗时：{1}",url,(endTime-startTime)));
		return result;
	}
	
	public String getHost(String url) {
		if (url == null || url.trim().equals("")) {
			return "";
		}
		String host = "";
		Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			host = matcher.group();
		}
		return host;
	}
	
	private void release(CloseableHttpResponse response,
						 HttpEntity resp_entity, CloseableHttpClient client) {
		Log.debug("开始释放 CloseableHttpResponse 与 entity,CloseableHttpClient timestamp:" + System.currentTimeMillis());
		try {
			if (resp_entity != null)
				EntityUtils.consume(resp_entity);
			if (response != null)
				response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.debug("释放 CloseableHttpResponse 与 entity,CloseableHttpClient 完成, timestamp:" + System.currentTimeMillis());
	}
	
	private HttpHost getProxyServer() {
		Log.debug(MessageFormat.format("开始从[{0}]个代理服务器中随机获取代理, timestamp:{1}", SERVER_IP.size(), System.currentTimeMillis()));
		HttpHost host = SERVER_IP.get(r.nextInt(SERVER_IP.size()));
		Log.debug(MessageFormat.format("选择好的代理服务器为：{0}, timestamp:{1}", host, System.currentTimeMillis()));
		return host;
	}
	
	private String getUC_Agent() {
		Random random = new Random();
		return AGENT_FLAG.get(random.nextInt(AGENT_FLAG.size()));
	}

	private CookieStore getCookieStore() {
		CookieStore cookieStore = new BasicCookieStore();
		for (int i = 0; i < cookieList.size(); i++) {
			cookieStore.addCookie(cookieList.get(i));
		}
		return cookieStore;
	}

	public static void main(String [] args){
		
//		long start = System.currentTimeMillis();
//		for (int i = 0; i < 1000; i++){
//			try {
//				HttpClient client = new HttpClient();
//				HttpResult result = client.doGet( "http://www.51jrq.com", null,"51job");
//			} catch (PlatformCloudExceptionGetHttpClient e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (PlatformCloudExceptionHttpRequest e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//System.out.println(new String(result.getResponseByte()));
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println(endTime - start);

//		HttpClient client = new HttpClient();
//		try {
//			client.download("http://mozilla.github.io/pdf.js/web/compressed.tracemonkey-pldi-09.pdf",
//					"F:/a.pdf", "51job");
//		} catch (PlatformCloudExceptionGetHttpClient e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
