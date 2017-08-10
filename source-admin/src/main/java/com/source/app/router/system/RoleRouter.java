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
import com.source.system.entity.Role;
import com.source.system.service.IRoleService;
import com.source.utils.ToolUtil;

@Controller
@RequestMapping(value = "/role")
public class RoleRouter extends BaseRouter {

	 @Autowired
     IRoleService roleService;
	
	@Override
	protected String getPrefix() {
		return "/admin/sys/role";
	}
	
	
	 /**
     * 跳转到修改角色
     */
    @RequestMapping(value = "/role_edit/{roleId}")
    public String roleEdit(@PathVariable Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Role role = this.roleService.selectById(roleId);
        model.addAttribute(role);
        model.addAttribute("pName", ConstantFactory.me().getSingleRoleName(role.getPid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(role.getDeptid()));
        //LogObjectHolder.me().set(role);
        return EDIT;
    }

    /**
     * 跳转到角色分配
     */
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", ConstantFactory.me().getSingleRoleName(roleId));
        return getPrefix() + "/role_assign";
    }

	
	
}
