package com.source.system.mapper;

import com.source.base.node.ZTreeNode;
import com.source.system.entity.Dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface DeptMapper extends BaseMapper<Dept> {

	
	List<ZTreeNode> tree();

    List<Map<String, Object>> list(@Param("condition") String condition);

}