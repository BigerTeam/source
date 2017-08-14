package com.source.app.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.source.base.controller.BaseController;
import com.source.base.model.vo.DataGrid;
import com.source.system.model.request.SystemLogRequest;
import com.source.system.service.ISystemLogService;

/**
 * SystemLog控制器
 * @create 2017-3-29 21:34:13
 */
@RestController
public class SystemLogController extends BaseController {

    @Autowired
    private ISystemLogService systemLogService;


    /**
     * 查询分页
     */
    @RequestMapping(value = "/sys/logs/page", method = RequestMethod.GET)
    public DataGrid getPage(SystemLogRequest request) {
        return buildDataGrid(systemLogService.getPage(getPagination(request),request));
    }
}
