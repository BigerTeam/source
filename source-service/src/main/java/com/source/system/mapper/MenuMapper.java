package com.source.system.mapper;

import com.source.system.entity.Menu;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

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
}