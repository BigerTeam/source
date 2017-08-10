package com.source.app.controller.system;


import java.util.Date;
import java.util.Map;

import javax.naming.NoPermissionException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.source.app.controller.AdminBaseController;
import com.source.app.factory.UserFactory;
import com.source.app.shiro.ShiroKit;
import com.source.app.shiro.ShiroUser;
import com.source.app.state.ManagerStatus;
import com.source.app.wrapper.system.UserWarpper;
import com.source.base.common.Const;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.model.vo.DataGrid;
import com.source.base.tips.Tip;
import com.source.system.entity.User;
import com.source.system.model.request.UserRequest;
import com.source.system.service.IUserService;
import com.source.utils.BeanCopier;
import com.source.utils.ToolUtil;

/**
 * 用户前端控制器
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends AdminBaseController{
	
	@Autowired
	private IUserService userService;
	
    /**
     * 获取管理员列表
     * @author zhuyangxu 
     * @data 2017年8月9日 下午9:46:56
     * @param request
     * @return
     */
    @RequestMapping(value = "/pages", method = RequestMethod.GET)
    @ResponseBody
    public DataGrid getPages(UserRequest request) {
    	Page<Map<String, Object>> page = userService.getUsersPage(getPagination(request), request);
        new UserWarpper(page.getRecords()).warp();
    	return super.buildDataGrid(page);
    }
    
    
    
    /**
     * 修改当前用户的密码
     */
    @RequestMapping("/changepw")
    @ResponseBody
    public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        long userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            userService.updateById(user);
            return SUCCESS_TIP;
        } else {
            throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }


    /**
     * 添加管理员
     */
    @RequestMapping("/add")
    @ResponseBody
    public Tip add(@Valid UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        // 判断账号是否重复
        User theUser = userService.getUser(userRequest.getAccount());
        if (theUser != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 完善账号信息
        userRequest.setSalt(ShiroKit.getRandomSalt(5));
        userRequest.setPassword(ShiroKit.md5(userRequest.getPassword(), userRequest.getSalt()));
        userRequest.setStatus(ManagerStatus.OK.getCode());
        userRequest.setCreatetime(new Date());
        User user =  BeanCopier.copy(userRequest, User.class);
        userService.insert(user);
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Tip edit(@Valid UserRequest user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            userService.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser.getId().equals(user.getId())) {
            	userService.updateById(UserFactory.createUser(user));
                return SUCCESS_TIP;
            } else {
                throw new BussinessException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Tip delete(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        userService.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return userService.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
    @RequestMapping("/reset")
    @ResponseBody
    public Tip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = userService.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        userService.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
    @RequestMapping("/freeze")
    @ResponseBody
    public Tip freeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
    @RequestMapping("/unfreeze")
    @ResponseBody
    public Tip unfreeze(@RequestParam Long userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        userService.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }
    
    /**
     * 分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    @RequestMapping("/setRole")
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        this.userService.setRoles(userId, roleIds);
        return SUCCESS_TIP;
    }
}
