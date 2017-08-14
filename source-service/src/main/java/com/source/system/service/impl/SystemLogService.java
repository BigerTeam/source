package com.source.system.service.impl;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baomidou.mybatisplus.plugins.Page;
import com.source.base.common.Const;
import com.source.base.service.impl.BaseService;
import com.source.log.aop.LogPoint;
import com.source.system.entity.SystemLog;
import com.source.system.mapper.SystemLogMapper;
import com.source.system.model.request.SystemLogRequest;
import com.source.system.model.response.SystemLogResponse;
import com.source.system.model.response.UserResponse;
import com.source.system.service.ISystemLogService;
import com.source.utils.IPUtils;
import com.source.utils.ShiroKit;

/**
 * SystemLog Service实现类
 */
@Service
public class SystemLogService extends BaseService<SystemLogMapper, SystemLog> implements ISystemLogService, LogPoint {

    private static final String LOG_CONTENT = "[类名]:%s,[方法]:%s,[参数]:%s,[IP]:%s";

    @Override
    @Transactional
    public void save(ProceedingJoinPoint joinPoint, String methodName, String module, String description) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String username = null;
        if (ShiroKit.getSession().getAttribute(Const.SESSION_USER) != null) {
        	UserResponse user = (UserResponse) ShiroKit.getSession().getAttribute(Const.SESSION_USER);
            username = user != null ? user.getAccount() : null;
        }
        String ip = IPUtils.getIpAddr(request);

        SystemLog log = new SystemLog();
        log.setUsername(username);
        log.setModule(module);
        log.setDescription(description);
        log.setIp(ip);
        log.setContent(operateContent(joinPoint, methodName, ip, request));

        super.insert(log);
    }

    @SuppressWarnings("unchecked")
	public String operateContent(ProceedingJoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] params = joinPoint.getArgs();
        StringBuffer bf = new StringBuffer();
        if (params != null && params.length > 0) {
            Enumeration<String> paraNames = request.getParameterNames();
            while (paraNames.hasMoreElements()) {
                String key = paraNames.nextElement();
                bf.append(key).append("=");
                bf.append(request.getParameter(key)).append("&");
            }
            if (StringUtils.isBlank(bf.toString())) {
                bf.append(request.getQueryString());
            }
        }
        return String.format(LOG_CONTENT, className, methodName, bf.toString(), ip);
    }

    @SuppressWarnings("unchecked")
	@Override
    public Page<SystemLogResponse> getPage(Page<SystemLog> page, SystemLogRequest request) {
        List<SystemLog> logs = baseMapper.findSystemLog(page, request);
        page.setRecords(logs);
        return convert(page, SystemLogResponse.class);
    }
}
