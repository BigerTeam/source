package com.source.app.controller.train;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;

import com.source.app.shiro.ShiroKit;
import com.source.utils.train.HttpRequestNg;

public class BaseController {
//	private String name="login";
//	public void setSession(HttpServletRequest request,String value){
//		HttpSession session = request.getSession();
//		session.setAttribute(name, value);
//	}
//	public void getSession(HttpServletRequest request){
//		HttpSession session = request.getSession();
//		session.getAttribute(name);
//	}
	public void clearSession(){
		Session session = ShiroKit.getSession();
		session.removeAttribute("username");
	}
	public static HttpRequestNg getHttpClient(HttpServletRequest request){
		HttpRequestNg httpclient=(HttpRequestNg) request.getAttribute("httpClient");
		return httpclient;
	}
}
