package com.source.app.authorization;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.source.base.common.Const;
import com.source.system.model.response.UserResponse;


/**
 * function:登录用户相关数据. <br/>
 */
public class SessionUtils {

    /**
     * 获取当前用户session.
     *
     * @return session
     */
    public static Session getSession() {
        Session session = SecurityUtils.getSubject().getSession();
        return session;
    }

    /**
     * 获取当前用户对象.
     *
     * @return user
     */
    public static UserResponse getCurrentUser() {
        Session session = SecurityUtils.getSubject().getSession();
        if (null != session) {
            return (UserResponse) session.getAttribute(Const.SESSION_USER);
        } else {
            return null;
        }
    }

    /**
     * 获取当前登录用户id.
     *
     * @return
     */
    public static Long getCurrentUserId() {
        UserResponse user = getCurrentUser();
        if (user != null) {
            return user.getId();
        }
		return null;
    }

    /**
     * 获取当前登录用户名.
     *
     * @return
     */
    public static String getCurrentUserName() {
        UserResponse user = getCurrentUser();
        if (user != null) {
            return user.getAccount();
        }
        return null;
    }
}
