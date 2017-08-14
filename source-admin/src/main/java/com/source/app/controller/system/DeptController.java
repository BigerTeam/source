package com.source.app.controller.system;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.source.app.controller.AdminBaseController;
import com.source.app.wrapper.system.DeptWarpper;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.node.ZTreeNode;
import com.source.log.annotation.Log;
import com.source.system.entity.Dept;
import com.source.system.mapper.DeptMapper;
import com.source.utils.ToolUtil;

/**
 * 部门控制器
 *
 */
@Controller
@RequestMapping("/system/dept")
public class DeptController extends AdminBaseController {


    @Resource
    DeptMapper deptMapper;


    /**
     * 获取部门的tree列表
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public List<ZTreeNode> tree() {
    	List<ZTreeNode> tree = new ArrayList<ZTreeNode>();
    	tree = deptMapper.tree();
        tree.add(ZTreeNode.createParent());
        return tree;
    }

    
  
    
    /**
     * 新增部门
     */
    @RequestMapping(value = "/add")
    @Log(module="部门模块",description="新增部门")
    @ResponseBody
    public Object add(Dept dept) {
        if (ToolUtil.isOneEmpty(dept, dept.getSimplename())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        return this.deptMapper.insert(dept);
    }

    /**
     * 获取所有部门列表
     */
    @RequestMapping(value = "/list")
    @Log(module="部门模块",description="获取部门列表")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.deptMapper.list(condition);
        return super.warpObject(new DeptWarpper(list));
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptId}")
    @ResponseBody
    public Object detail(@PathVariable("deptId") Integer deptId) {
        return deptMapper.selectById(deptId);
    }

    /**
     * 修改部门
     */
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/update")
    @Log(module="部门模块",description="修改部门")
    @ResponseBody
    public Object update(Dept dept) {
        if (ToolUtil.isEmpty(dept) || dept.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        deptMapper.updateById(dept);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/delete")
    @Log(module="部门模块",description="删除部门")
    @ResponseBody
    public Object delete(@RequestParam Integer deptId) {
        deptMapper.deleteById(deptId);
        return SUCCESS_TIP;
    }


}
