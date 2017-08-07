package com.source.utils.train;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsRequestNg extends HttpRequestNg{
	private final static Logger logger=LoggerFactory.getLogger(HttpsRequestNg.class);
	private static HttpRequestNg httpRequestNg;

	public HttpsRequestNg(){
		try {
//			HttpsURLConnection.setFollowRedirects(true);
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tms = { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate certificates[], String authType) throws CertificateException {}
				public void checkServerTrusted(X509Certificate[] ax509certificate, String s) throws CertificateException {}
				public X509Certificate[] getAcceptedIssuers() { return null;}
			}};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tms, new SecureRandom());
			//从上述SSLContext对象中得到SSLSocketFactory对象
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		} catch (KeyManagementException e) {
			logger.error("",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		} catch (NoSuchProviderException e) {
			logger.error("",e);
		} 
	}
	public static HttpRequestNg getHttpClient() {
		if(httpRequestNg==null)
			httpRequestNg=new HttpsRequestNg();
		return httpRequestNg;
	}
	public static void setHttpClient(HttpRequestNg httpRequestNg) {
		HttpsRequestNg.httpRequestNg=httpRequestNg;
	}
}
