package com.source.app.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.session.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.baomidou.mybatisplus.plugins.Page;
import com.source.app.shiro.ShiroKit;
import com.source.app.utils.HttpKit;
import com.source.base.controller.BaseController;
import com.source.base.tips.SuccessTip;
import com.source.base.warpper.BaseControllerWarpper;
import com.source.utils.FileUtil;
import com.source.utils.train.HttpRequestNg;

public abstract class AdminBaseController extends BaseController{
	
	
	
	   protected static String SUCCESS = "SUCCESS";
	    protected static String ERROR = "ERROR";

	    protected static String REDIRECT = "redirect:";
	    protected static String FORWARD = "forward:";

	    protected static SuccessTip SUCCESS_TIP = new SuccessTip();

	    protected HttpServletRequest getHttpServletRequest() {
	        return HttpKit.getRequest();
	    }

	    protected HttpServletResponse getHttpServletResponse() {
	        return HttpKit.getResponse();
	    }

	    protected HttpSession getSession() {
	        return HttpKit.getRequest().getSession();
	    }

	    protected HttpSession getSession(Boolean flag) {
	        return HttpKit.getRequest().getSession(flag);
	    }

	    protected String getPara(String name) {
	        return HttpKit.getRequest().getParameter(name);
	    }

	    protected void setAttr(String name, Object value) {
	        HttpKit.getRequest().setAttribute(name, value);
	    }



	    /**
	     * 包装一个list，让list增加额外属性
	     */
	    protected Object warpObject(BaseControllerWarpper warpper) {
	        return warpper.warp();
	    }

	    /**
	     * 删除cookie
	     */
	    protected void deleteCookieByName(String cookieName) {
	        Cookie[] cookies = this.getHttpServletRequest().getCookies();
	        for (Cookie cookie : cookies) {
	            if (cookie.getName().equals(cookieName)) {
	                Cookie temp = new Cookie(cookie.getName(), "");
	                temp.setMaxAge(0);
	                this.getHttpServletResponse().addCookie(temp);
	            }
	        }
	    }

	    /**
	     * 返回前台文件流
	     */
	    protected ResponseEntity<byte[]> renderFile(String fileName, String filePath) {
	        byte[] bytes = FileUtil.toByteArray(filePath);
	        return renderFile(fileName, bytes);
	    }

	    /**
	     * 返回前台文件流
	     */
	    protected ResponseEntity<byte[]> renderFile(String fileName, byte[] fileBytes) {
	        String dfileName = null;
	        try {
	            dfileName = new String(fileName.getBytes("gb2312"), "iso8859-1");
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentDispositionFormData("attachment", dfileName);
	        return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
	    }
	    
	    /**
	     * 清楚火车票的用户名session
	     * @author zhuyangxu 
	     * @data 2017年8月14日 上午9:41:46
	     */
	    public void clearSession(){
			Session session = ShiroKit.getSession();
			session.removeAttribute("username");
		}
		public static HttpRequestNg getHttpClient(HttpServletRequest request){
			HttpRequestNg httpclient=(HttpRequestNg) request.getAttribute("httpClient");
			return httpclient;
		}
}
