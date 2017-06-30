package com.fiveone.util;


import com.fiveone.excelption.PlatformCloudExceptionGetHttpClient;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获得
 * @author xudelin
 *
 */
public class HttpConnectionManager {
	
	private static Map<String, CloseableHttpClient> client_pool = new  LinkedHashMap<String, CloseableHttpClient>();
	private static PoolingHttpClientConnectionManager poolConnManager ;
	private static int maxTotalPool = 10;
	private static int maxConPerRoute = 20;
	private static int socketTimeout = 5000;
	private static int connectTimeout = 5000;
	private static int connectionRequestTimeout = 10000;
	private static Logger log = Logger.getLogger(HttpConnectionManager.class);
	

      
	
    static{ 
         try {  
        	KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, new SSLAnyTrustStrategy()).build();
            HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,hostnameVerifier);  
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf)
                    .build();  
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolConnManager.setMaxTotal(maxTotalPool);
            poolConnManager.setDefaultMaxPerRoute(maxConPerRoute);
            SocketConfig socketConfig = SocketConfig.custom().setSoTimeout(socketTimeout).build();
            poolConnManager.setDefaultSocketConfig(socketConfig);  
        } catch (Exception e) {  
            log.error("初始化Http Client 连接池 异常："+e.toString()); 
        } 
    }
    
    /*public static CloseableHttpClient getHttpClient(String hostKey) throws PlatformCloudExceptionGetHttpClient  
    {  
    	if(! client_pool.containsKey(hostKey)){
    		synchronized(log){
    			if(! client_pool.containsKey(hostKey)){
    				CloseableHttpClient client = getHttpClient();
    				client_pool.put(hostKey, client);
    			}
    		}
    	}
    	return client_pool.get(hostKey);
    }*/
    
    public static CloseableHttpClient getHttpClient(String hostKey, CookieStore cookies) throws PlatformCloudExceptionGetHttpClient
    {  
    	if(! client_pool.containsKey(hostKey)){
    		synchronized(log){
    			if(! client_pool.containsKey(hostKey)){
    				CloseableHttpClient client = getHttpClient(cookies);
    				client_pool.put(hostKey, client);
    			}
    		}
    	}
    	return client_pool.get(hostKey);
    }
    
    
    private static CloseableHttpClient getHttpClient(CookieStore cookies) throws PlatformCloudExceptionGetHttpClient{
    	try{
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
		    		.setConnectTimeout(connectTimeout).setSocketTimeout(socketTimeout).build();  
			CloseableHttpClient httpClient ;
			if(cookies != null){
				httpClient = HttpClients.custom().setDefaultCookieStore(cookies)
		                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
			}else{
				httpClient = HttpClients.custom()
		                .setConnectionManager(poolConnManager).setDefaultRequestConfig(requestConfig).build();
			}
		    
		    if(poolConnManager != null && poolConnManager.getTotalStats() != null){  
		        log.info("目前连接池状态 " + poolConnManager.getTotalStats().toString());  
		    }
		    return httpClient;
    	}catch(Exception e){
    		throw new PlatformCloudExceptionGetHttpClient("无法获取HTTP Client连接：","100003",e);
    	}
	}
    
    /*
    public static void releaseConnection(PoolingHttpClientConnectionManager pool,CloseableHttpClient client){
    	pool.releaseConnection((HttpClientConnection) client, "等待释放该连接！", 300, TimeUnit.SECONDS);
    }*/
    
    
}
