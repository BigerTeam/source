package com.source.system.service;

import com.source.system.entity.Menu;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;

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
	 * @author zhuyangxu 
	 * @data 2017年7月17日 下午3:56:23
	 * @param roleId
	 * @return
	 */
	List<String> getResUrlsByRoleId(Integer roleId);
	
}
