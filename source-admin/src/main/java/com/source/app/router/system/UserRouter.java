package com.source.app.router.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.source.app.constant.factory.ConstantFactory;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.router.BaseRouter;
import com.source.system.entity.User;
import com.source.system.service.IUserService;
import com.source.utils.ToolUtil;

/**
 * 用户路由
 * @author zhuyangxu
 * @date 2017年8月10日上午9:19:16
 */
@Controller
@RequestMapping(value = "/user")
public class UserRouter extends BaseRouter {

	@Autowired
	private IUserService usreService;
	
	@Override
	protected String getPrefix() {
		return "/admin/sys/user";
	}

	
	/**
	 * 重新写的编辑方法
	 * @author zhuyangxu 
	 * @data 2017年8月10日 上午9:18:53
	 * @param userId
	 * @param model
	 * @return
	 */
    @RequestMapping("/new_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = usreService.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        return EDIT;
    }
	


}
