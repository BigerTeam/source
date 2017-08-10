package com.source.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.source.base.node.MenuNode;
import com.source.base.node.ZTreeNode;
import com.source.base.service.impl.BaseService;
import com.source.system.entity.Menu;
import com.source.system.mapper.MenuMapper;
import com.source.system.service.IMenuService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Service
public class MenuServiceImpl extends BaseService<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<String> getResUrlsByRoleId(Integer roleId) {
		return menuMapper.getResUrlsByRoleId(roleId);
	}

	@Override
	public List<Map<String, Object>> selectMenus(String condition, String level) {
		return menuMapper.selectMenus(condition, level);
	}

	@Override
	public List<Integer> getMenuIdsByRoleId(Integer roleId) {
		return menuMapper.getMenuIdsByRoleId(roleId);
	}

	@Override
	public List<ZTreeNode> menuTreeList() {
		return menuMapper.menuTreeList();
	}

	@Override
	public List<ZTreeNode> menuTreeListByMenuIds(List<Integer> menuIds) {
		return menuMapper.menuTreeListByMenuIds(menuIds);
	}

	@Override
	public int deleteRelationByMenu(Integer menuId) {
		return menuMapper.deleteRelationByMenu(menuId);
	}

	@Override
	public List<MenuNode> getMenusByRoleIds(List<Integer> roleIds) {
		return menuMapper.getMenusByRoleIds(roleIds);
	}

	@Override
	public void delMenu(Integer menuId) {
		// 删除菜单
		this.menuMapper.deleteById(menuId);
		// 删除关联的relation
		this.menuMapper.deleteRelationByMenu(menuId);
	}

	@Override
	public void delMenuContainSubMenus(Integer menuId) {
		Menu menu = menuMapper.selectById(menuId);
		// 删除当前菜单
		delMenu(menuId);
		// 删除所有子菜单
		Wrapper<Menu> wrapper = new EntityWrapper<>();
		wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
		List<Menu> menus = menuMapper.selectList(wrapper);
		for (Menu temp : menus) {
			delMenu(temp.getId());
		}
	}

}
