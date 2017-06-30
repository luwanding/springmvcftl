package com.fiveone.util;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 获得HTTPS Client实例类
 * @author xudelin
 *
 */
 
@SuppressWarnings("deprecation")
public class HttpsClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpsClient.class);  
    
	public static CloseableHttpClient getHttpsClient() {  
        try {  
        	  SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
        		  //信任所有
                  public boolean isTrusted(X509Certificate[] chain,
                                  String authType) throws CertificateException {
                      return true;
                  }
              }).build();
              SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
              return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (Exception ex) {
            LOG.error("创建HTTPS安全连接时出现异常：", ex);
            return HttpClients.createDefault();
        }  
    }  
   
}
