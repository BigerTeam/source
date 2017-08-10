package com.source.base.common;

/**
 * 常量类
 */
public interface Const {

    /** session中存放的用户key*/
    public final static String SESSION_USER = "user";
    
    
    public final static String SESSION_ShiroUSER = "ShiroUser";

    /**
     * 系统默认的管理员密码
     */
    String DEFAULT_PWD = "111111";

    /**
     * 管理员角色的名字
     */
    String ADMIN_NAME = "administrator";

    /**
     * 管理员id
     */
    Integer ADMIN_ID = 1;

    /**
     * 超级管理员角色id
     */
    Integer ADMIN_ROLE_ID = 1;

    /**
     * 接口文档的菜单名
     */
    String API_MENU_NAME = "接口文档";

}
