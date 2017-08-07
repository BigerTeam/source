package com.source.app.shiro.check;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.source.app.shiro.ShiroKit;
import com.source.app.shiro.ShiroUser;
import com.source.app.utils.HttpKit;
import com.source.app.utils.SpringContextHolder;
import com.source.base.listener.ConfigListener;
import com.source.utils.CollectionKit;

/**
 * 权限自定义检查
 */
@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class PermissionCheckFactory implements ICheck {

    public static ICheck me() {
        return SpringContextHolder.getBean(ICheck.class);
    }

    @Override
    public boolean check(Object[] permissions) {
        ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            return false;
        }
        String join = CollectionKit.join(permissions, ",");
        if (ShiroKit.hasAnyRoles(join)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkAll() {
        HttpServletRequest request = HttpKit.getRequest();
        ShiroUser user = ShiroKit.getUser();
        if (null == user) {
            return false;
        }
        String requestURI = request.getRequestURI().replace(ConfigListener.getConf().get("contextPath"), "");
        String[] str = requestURI.split("/");
        if (str.length > 3) {
            requestURI = "/" + str[1] + "/" + str[2];
        }
        if (ShiroKit.hasPermission(requestURI)) {
            return true;
        }
        return false;
    }

}
