package com.source.system.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.service.IService;
import com.source.base.node.MenuNode;
import com.source.base.node.ZTreeNode;
import com.source.system.entity.Menu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 根据角色ID 获取菜单
	 * @param roleId
	 * @return
	 */
	List<String> getResUrlsByRoleId(Integer roleId);
	
	
	/**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     */
    int deleteRelationByMenu(Integer menuId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);
    
    
    /**
     * 删除菜单
     *
     */
    void delMenu(Integer menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     */
    void delMenuContainSubMenus(Integer menuId);

	
}
