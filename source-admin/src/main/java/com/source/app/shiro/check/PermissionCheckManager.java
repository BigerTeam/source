package com.source.app.shiro.check;

import com.source.app.utils.SpringContextHolder;

/**
 * 权限检查工厂
 */
public class PermissionCheckManager {
    private final static PermissionCheckManager me = new PermissionCheckManager();

    private ICheck defaultCheckFactory = SpringContextHolder.getBean(ICheck.class);

    public static PermissionCheckManager me() {
        return me;
    }

    private PermissionCheckManager() {
    }

    public PermissionCheckManager(ICheck checkFactory) {
        this.defaultCheckFactory = checkFactory;
    }

    public void setDefaultCheckFactory(ICheck defaultCheckFactory) {
        this.defaultCheckFactory = defaultCheckFactory;
    }

    public static boolean check(Object[] permissions) {
        return me.defaultCheckFactory.check(permissions);
    }

    public static boolean checkAll() {
        return me.defaultCheckFactory.checkAll();
    }
}
