package com.source.system.service;

import com.source.system.entity.User;
import com.source.system.model.request.UserRequest;
import com.source.system.model.response.UserResponse;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface IUserService extends IService<User> {

	UserResponse get(String username);

	User getUser(String username);

	
	
	/**
	 * 分页获取用户列表
	 * @author zhuyangxu 
	 * @data 2017年8月9日 上午10:22:12
	 * @param pagination
	 * @param request
	 * @return
	 */
	Page<Map<String, Object>> getUsersPage(Page<Map<String, Object>> pagination, UserRequest request);

	 public void setStatus(Long userId, int code);

	
}
