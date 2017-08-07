package com.source.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.source.base.exception.ApplicationException;
import com.source.base.exception.StatusCode;
import com.source.base.service.impl.BaseService;
import com.source.system.entity.User;
import com.source.system.mapper.MenuMapper;
import com.source.system.mapper.UserMapper;
import com.source.system.model.response.UserResponse;
import com.source.system.service.IUserService;
import com.source.utils.BeanCopier;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Service
public class UserService  extends BaseService<UserMapper, User> implements IUserService{

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public UserResponse get(String username) {
		User existing = getUserByUsername(username);
		 if(existing!=null){
	            return BeanCopier.copy(existing, UserResponse.class);
	        }else{
	            //用户不存在
	            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), StatusCode.NOT_FOUND.getMessage());
	        }
	}

	
	
	private User getUserByUsername(String username){
		return this.selectOne(new EntityWrapper<User>().eq("account", username));
	}



	@Override
	public User getUser(String username) {
		return getUserByUsername(username);
	}


	

	@Override
	public List<Map<String, Object>> selectUsers(String name, String beginTime, String endTime, Integer deptid) {
		List<String> list = menuMapper.getResUrlsByRoleId(1);
		System.out.println(list);
		return userMapper.selectUsers(name, beginTime, endTime, deptid);

	}
}
