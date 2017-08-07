package com.source.app.controller.system;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.source.app.shiro.factory.ShiroFactroy;
import com.source.app.wrapper.system.UserWarpper;
import com.source.base.annotation.Permission;
import com.source.base.controller.BaseController;
import com.source.system.service.IUserService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController{
	
	@Autowired
	private IUserService usreService;
	
    @RequestMapping("/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
    	ShiroFactroy.me().findPermissionsByRoleId(1);
    	List<Map<String, Object>> users = usreService.selectUsers(name, beginTime, endTime, deptid);
        return new UserWarpper(users).warp();
    }
	
}
