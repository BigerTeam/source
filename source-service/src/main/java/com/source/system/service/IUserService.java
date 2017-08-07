package com.source.system.service;

import com.source.system.entity.User;
import com.source.system.model.response.UserResponse;

import java.util.List;
import java.util.Map;

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

	
	
	List<Map<String, Object>> selectUsers(String name, String beginTime, String endTime, Integer deptid);
	
}
