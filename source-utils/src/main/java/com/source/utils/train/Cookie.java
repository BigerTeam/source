package com.source.utils.train;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cookie {
	private final static Logger logger=LoggerFactory.getLogger(Cookie.class);

	private String name;
	private String value;
	private String domain;
	private String path;
	private int maxAge;
	private boolean secure;
	private String comment;
	private Date expires;
	private int version;
	private String cookie;
	
	public Cookie(String cookie) {
		this.cookie = cookie;
		this.value = "";
	}
	/**
	 * @param name
	 * @param value
	 */
	public Cookie(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	/**
	 * @param name
	 * @param value
	 * @param domain
	 * @param path
	 */
	public Cookie(String name, String value, String domain, String path) {
		super();
		this.name = name;
		this.value = value;
		this.domain = domain;
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	public boolean isSecure() {
		return secure;
	}
	public void setSecure(boolean secure) {
		this.secure = secure;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	@Override
	public String toString() {
		return cookie;
	}
	
	public static Cookie parse(String cookieString){
		try {
			Cookie cookie = new Cookie(cookieString);
			String namevalue = cookieString.substring(0,cookieString.indexOf(";"));
			String[] keyValuePair = namevalue.split("=");
			cookie.name = keyValuePair[0];
			if(keyValuePair.length>1) cookie.value = keyValuePair[1];
			return cookie;
		} catch (Exception e) {
			System.out.println(cookieString);
			logger.error("",e);
		}
		return null;
	}
	
}
