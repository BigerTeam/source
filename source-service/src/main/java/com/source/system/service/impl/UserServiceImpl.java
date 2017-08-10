package com.source.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.source.base.exception.ApplicationException;
import com.source.base.exception.StatusCode;
import com.source.base.service.impl.BaseService;
import com.source.system.entity.User;
import com.source.system.mapper.MenuMapper;
import com.source.system.mapper.UserMapper;
import com.source.system.model.request.UserRequest;
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
public class UserServiceImpl  extends BaseService<UserMapper, User> implements IUserService{

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
	public Page<Map<String, Object>> getUsersPage(Page<Map<String, Object>> page, UserRequest request){
		 List<Map<String, Object>> users = userMapper.selectUsers(page,request.getName(),request.getSex()==null?0:request.getSex(),request.getPhone(), null, null, request.getDeptid());
		 page.setRecords(users);
		 return page;
	}



	@Override
	public void setStatus(Long userId, int code) {
		User user = new User();
		user.setId(userId);
		user.setStatus(code);
		userMapper.updateById(user);
	}



	@Override
	public void setRoles(Integer userId, String roleIds) {
		userMapper.setRoles(userId, roleIds);
	}
	
	
}
