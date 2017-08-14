package com.source.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.source.base.service.IBaseService;
import com.source.system.entity.SystemLog;
import com.source.system.model.request.SystemLogRequest;
import com.source.system.model.response.SystemLogResponse;


/**
 * SystemLog Service接口
 */
public interface ISystemLogService extends IBaseService<SystemLog> {

    public Page<SystemLogResponse> getPage(Page<SystemLog> page, SystemLogRequest request);
}
