package com.source.utils.train;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestNg {
	private final static Logger logger=LoggerFactory.getLogger(HttpRequestNg.class);

	protected Properties headers = new Properties();
	public Map<String,Cookie> cookies = new HashMap<String, Cookie>();
	
	public String encoding = "UTF-8";
	
	public HttpRequestNg(){
		super();
	}
	public HttpRequestNg(String encoding){
		try {
			System.out.println(HttpRequestNg.class.getResource("/").getPath());
			headers.load(new FileInputStream(HttpRequestNg.class.getResource("/").getPath()+"headers.properties"));
			if(encoding!=null && encoding.length()!=0)
				this.encoding = encoding;
		} catch (IOException e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 设置请求头
	 * @param httpURLConnection
	 * @throws IOException
	 */
	private void setHttpRequestHeader(HttpURLConnection httpURLConnection) throws IOException{
		//设置通用请求头
		Enumeration<Object> keys = headers.keys();
		while(keys.hasMoreElements()){
			String key = (String)keys.nextElement();
			String value = headers.getProperty(key);
			httpURLConnection.setRequestProperty(key, value);
		}
		//拼cookie字符串
		StringBuffer cookieString = new StringBuffer();
		Set<Map.Entry<String, Cookie>> entrys = cookies.entrySet();
		Cookie current=null;
		for (Map.Entry<String, Cookie> entry : entrys) {
			Cookie cookie = entry.getValue();
			if(!"current_captcha_type".equals(cookie.getName())&&!"BIGipServerotn".equals(cookie.getName()))
				cookieString.append(cookie.getName()+"="+cookie.getValue()+"; ");
			else if(!"current_captcha_type".equals(cookie.getName())){
				current=cookie;
			}
		}
		if(current!=null)
			cookieString.append(current.getName()+"="+current.getValue()+"; ");
		httpURLConnection.setRequestProperty("Cookie", cookieString.toString());
		httpURLConnection.setRequestProperty("Host", httpURLConnection.getURL().getHost());
	}
	
	/**
	 * @param uri
	 * @param queryStringParameters
	 * @param method
	 * @return
	 * @throws IOException 
	 */
	protected byte[] service(String uri, String queryStringParameters, String method) throws IOException {
		if ("GET".equals(method)) {
			uri = correctRequestURLAndParameter(uri, queryStringParameters);
		}
		URL url = new URL(uri);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setInstanceFollowRedirects(false);
		con.setRequestMethod(method);
		con.setDoInput(true);
		con.setDoOutput(true);
		setHttpRequestHeader(con);
		if("POST".equals(method)) {
			queryStringParameters = correctRequestParameter(queryStringParameters);
			OutputStream os = con.getOutputStream();
			os.write(queryStringParameters.getBytes());
			os.flush();
			os.close();
		}
		switch (con.getResponseCode()) {
			case 200:
			case 404:
			case 411:
			case 500:
				InputStream is = con.getInputStream();
				BufferByte buffer = new BufferByte();
				byte[] bs = new byte[1024];
				int len = -1;
				while((len=is.read(bs))!=-1){
					buffer.append(bs, 0, len);
				}
				is.close();
				//从响应头中获取Cookie
				Set<Map.Entry<String, List<String>>> responseHeaders = con.getHeaderFields().entrySet();
				for (Map.Entry<String, List<String>> entry : responseHeaders) {
					if("Set-Cookie".equals(entry.getKey())){
						for (int i = 0; i < entry.getValue().size(); i++) {
							Cookie cookie = Cookie.parse(entry.getValue().get(i));
							cookies.put(cookie.getName(),cookie);
						} 
					}
				}
				con.disconnect();
				headers.put("Referer", uri);
				return buffer.getBuffer();
			case 302:
				String location = con.getHeaderField("Location");
				return doGet(location);
			default:
				logger.info("Error", "暂不支持的响应码["+con.getResponseCode()+"]");
				return new byte[0];
		}
	}
	
	public byte[] doGet(String uri) throws IOException {
		return doGet(uri, "");
	}
	
	public byte[] doGet(String uri,String queryStringParameters) throws IOException {
		return service(uri, queryStringParameters, "GET");
	}
	
	public byte[] doPost(String uri) throws IOException {
		return doPost(uri, "");
	}
	
	public byte[] doPost(String uri,String queryStringParameters) throws IOException {
		return service(uri, queryStringParameters, "POST");
	}
	
	/**
	 * 矫正请求URL和参数
	 * 详情看代码注释
	 * @param url can be null or empty string
	 * @param queryStringParameters
	 * @return
	 */
	public static String correctRequestURL(String url){
		return correctRequestURLAndParameter(url,null);	
	}
	public static String correctRequestParameter(String queryStringParameter){
		return correctRequestURLAndParameter(null,queryStringParameter);	
	}
	public static String correctRequestURLAndParameter(String url,String queryStringParameter){
		
		if (url!=null && url.trim().length()!=0) {
			url = url.replaceAll("&+$", "");//例如URL为：http://www.a.com/a.jsp?a=b& 替换后为: 将http://www.a.com/a.jsp?a=b
			url = url.replaceAll("\\?+$", "");//例如URL为：http://www.a.com/a.jsp? 		替换后为: 将http://www.a.com/a.jsp
		}
		
		if (queryStringParameter!=null && queryStringParameter.trim().length()!=0) {
			queryStringParameter = queryStringParameter.trim().replaceAll("^&+", ""); //例如queryStringParameter为   &a=1&b=2&c=3     替换后为: a=1&b=2&c=3
			queryStringParameter = queryStringParameter.replaceAll("&+$", ""); //例如queryStringParameter为   a=1&b=2&c=3&  替换后为: a=1&b=2&c=3
		}
		
		//自动拼接
		if (url!=null && url.trim().length()!=0 && queryStringParameter!=null && queryStringParameter.trim().length()!=0)
			return url.indexOf("?")==-1 ? url + "?" + queryStringParameter : url + "&" + queryStringParameter;
		else if (url!=null && url.trim().length()!=0) 
			return url;
		else
			return queryStringParameter;
	}
	public static final int MESSAGE_TYPE_REQUEST_URL = 1;
	
	public static final int MESSAGE_TYPE_REQUEST_METHOD = 2;
	
	public static final int MESSAGE_TYPE_REQUEST_QUERY_STRING_PARAMETERS = 3;
	
	public static final int MESSAGE_TYPE_REQUEST_FORM_DATA = 4;
	
	public static final int MESSAGE_TYPE_REQUEST_HEADER = 5;
	
	public static final int MESSAGE_TYPE_REQUEST_COOKIE = 6;
	
	public static final int MESSAGE_TYPE_STATUS_CODE = 7;
	
	public static final int MESSAGE_TYPE_RESPONSE_COOKIE = 8;
	
	public static final int MESSAGE_TYPE_RESPONSE_HEADER = 9;
	
	public static final int MESSAGE_TYPE_RESPONSE = 10;
	
}
