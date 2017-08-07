package com.source.utils.train;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookieUtil {
	private final static Logger logger=LoggerFactory.getLogger(CookieUtil.class);

    private static Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(".kyfw.12306.cn");
        cookie.setMaxAge(86400 * 10000);//10000 DAYS
        return cookie;

    }

    /**
     * 设置COOKIE
     *
     * @param resp
     * @param cookieName
     * @param cookieValue
     */
    public static void setCookie(HttpServletResponse resp, String cookieName, String cookieValue) {

        resp.addCookie(createCookie(cookieName, cookieValue));
    }

    /**
     * 获取COOKIE
     *
     * @param request
     * @param cookieName
     * @return String
     */
    public static String getCookie(HttpServletRequest request, String cookieName) {
        if (request == null) return null;
        if (cookieName == null) return null;
        if (cookieName.length() == 0) return null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
        {}
        else {
            logger.debug("cookie size:" + cookies.length);
            for (Cookie c : cookies) {
                logger.debug("cookie : " + c.getName() + " / " + c.getValue());
                if (c.getName().equalsIgnoreCase(cookieName)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }
}
