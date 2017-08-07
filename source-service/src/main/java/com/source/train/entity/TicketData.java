package com.source.train.entity;

import java.io.Serializable;
import java.util.Map;

import com.source.utils.train.Cookie;
/**
 * 初始化文件加载
 */
public class TicketData implements Serializable {
	private static final long serialVersionUID = -2001688171145623261L;
	private String username;
	private String password;
	private Map<String,Cookie> map;
	
	public TicketData(String username, String password, Map<String, Cookie> map) {
		super();
		this.username = username;
		this.password = password;
		this.map = map;
	}
	@Override
	public String toString() {
		return "TicketData [username=" + username + ", password=" + password + ", map=" + map + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Map<String, Cookie> getMap() {
		return map;
	}
	public void setMap(Map<String, Cookie> map) {
		this.map = map;
	}
}
