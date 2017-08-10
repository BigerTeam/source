package com.source.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.source.base.node.ZTreeNode;
import com.source.system.entity.Role;

/**
 *  服务类
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface IRoleService extends IService<Role> {
	
	
	 /**
     * 根据条件查询角色列表
     *
     * @return
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     */
    List<ZTreeNode> roleTreeList();

    /**
     * 获取角色列表树
     * @return
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);
    
    /**
     * 设置权限
     * @param ids
     */
    void setAuthority(Integer roleId, String ids);

    /**
     * 删除角色
     */
    void delRoleById(Integer roleId);

}
