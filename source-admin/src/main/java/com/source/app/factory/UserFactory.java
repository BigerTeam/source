package com.source.app.factory;

import org.springframework.beans.BeanUtils;

import com.source.system.entity.User;
import com.source.system.model.request.UserRequest;

public class UserFactory {

	
	public static User createUser(UserRequest userDto){
        if(userDto == null){
            return null;
        }else{
            User user = new User();
            BeanUtils.copyProperties(userDto,user);
            return user;
        }
    }
}
