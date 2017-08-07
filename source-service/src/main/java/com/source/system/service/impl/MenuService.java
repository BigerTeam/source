package com.source.system.service.impl;

import com.source.system.entity.Menu;
import com.source.system.mapper.MenuMapper;
import com.source.system.service.IMenuService;
import com.source.base.service.impl.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Service
public class MenuService extends BaseService<MenuMapper, Menu> implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	
	@Override
	public List<String> getResUrlsByRoleId(Integer roleId) {
		return menuMapper.getResUrlsByRoleId(roleId);
	}
	
}
