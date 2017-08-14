package com.source.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.source.base.mapper.BaseMapper;
import com.source.system.entity.SystemLog;
import com.source.system.model.request.SystemLogRequest;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 */
public interface SystemLogMapper extends BaseMapper<SystemLog> {
    public List<SystemLog> findSystemLog(Pagination page, @Param("request") SystemLogRequest request);


}