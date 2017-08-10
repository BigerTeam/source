package com.source.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.source.base.node.MenuNode;
import com.source.base.node.ZTreeNode;
import com.source.system.entity.Menu;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface MenuMapper extends BaseMapper<Menu> {
	

	public List<String> getResUrlsByRoleId(Integer roleId);
	
	
	 /**
     * 根据条件查询菜单
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition,@Param("level") String level);

    /**
     * 根据条件查询菜单
     */
    List<Integer> getMenuIdsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取菜单列表树
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds);

    /**
     * 删除menu关联的relation
     *
     */
    int deleteRelationByMenu(Integer menuId);


    /**
     * 根据角色获取菜单
     *
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);

	
}