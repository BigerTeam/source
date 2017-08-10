package com.source.app.router.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.source.app.constant.factory.ConstantFactory;
import com.source.base.router.BaseRouter;
import com.source.system.entity.Dept;
import com.source.system.service.IDeptService;

/**
 * 部门路由
 * @author zhuyangxu
 * @date 2017年8月10日上午9:19:16
 */
@Controller
@RequestMapping(value = "/dept")
public class DeptRouter extends BaseRouter {

	@Autowired
	private IDeptService deptService;

	@Override
	protected String getPrefix() {
		return "/admin/sys/dept";
	}

	/**
     * 跳转到修改部门
     */
    @RequestMapping("/dept_update/{deptId}")
    public String deptUpdate(@PathVariable Integer deptId, Model model) {
        Dept dept = deptService.selectById(deptId);
        model.addAttribute(dept);
        model.addAttribute("pName", ConstantFactory.me().getDeptName(dept.getPid()));
        return  EDIT;
    }

	
	
	
	

}
