package com.source.app.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.source.app.constant.factory.ConstantFactory;
import com.source.app.controller.AdminBaseController;
import com.source.app.state.MenuStatus;
import com.source.app.wrapper.system.MenuWarpper;
import com.source.base.annotation.Permission;
import com.source.base.common.Const;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.node.ZTreeNode;
import com.source.base.tips.Tip;
import com.source.system.entity.Menu;
import com.source.system.service.IMenuService;
import com.source.utils.BeanKit;
import com.source.utils.ToolUtil;

/**
 * 菜单控制器
 *
 */
@Controller
@RequestMapping("/system/menu")
public class MenuController extends AdminBaseController {

	@Resource
	IMenuService menuService;

	

	/**
	 * 修该菜单
	 */
	@RequestMapping(value = "/edit")
	@ResponseBody
	public Tip edit(@Valid Menu menu, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 设置父级菜单编号
		menuSetPcode(menu);
		this.menuService.updateById(menu);
		return SUCCESS_TIP;
	}

	/**
	 * 获取菜单列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
		List<Map<String, Object>> menus = this.menuService.selectMenus(menuName, level);
		return super.warpObject(new MenuWarpper(menus));
	}

	/**
	 * 新增菜单
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public Tip add(@Valid Menu menu, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 设置父级菜单编号
		menuSetPcode(menu);

		menu.setStatus(MenuStatus.ENABLE.getCode());
		this.menuService.insert(menu);
		return SUCCESS_TIP;
	}

	/**
	 * 删除菜单
	 */
	@RequestMapping(value = "/remove")
	@ResponseBody
	public Tip remove(@RequestParam Integer menuId) {
		if (ToolUtil.isEmpty(menuId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}

		// 缓存菜单的名称
		// LogObjectHolder.me().set(ConstantFactory.me().getMenuName(menuId));
		this.menuService.delMenuContainSubMenus(menuId);
		return SUCCESS_TIP;
	}

	/**
	 * 查看菜单
	 */
	@RequestMapping(value = "/view/{menuId}")
	@ResponseBody
	public Tip view(@PathVariable Integer menuId) {
		if (ToolUtil.isEmpty(menuId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		this.menuService.selectById(menuId);
		return SUCCESS_TIP;
	}

	/**
	 * 获取菜单列表(首页用)
	 */
	@RequestMapping(value = "/menuTreeList")
	@ResponseBody
	public List<ZTreeNode> menuTreeList() {
		List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
		return roleTreeList;
	}

	/**
	 * 获取菜单列表(选择父级菜单用)
	 */
	@RequestMapping(value = "/selectMenuTreeList")
	@ResponseBody
	public List<ZTreeNode> selectMenuTreeList() {
		List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
		roleTreeList.add(ZTreeNode.createParent());
		return roleTreeList;
	}

	/**
	 * 获取角色列表
	 */
	@RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
	@ResponseBody
	public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
		List<Integer> menuIds = this.menuService.getMenuIdsByRoleId(roleId);
		if (ToolUtil.isEmpty(menuIds)) {
			List<ZTreeNode> roleTreeList = this.menuService.menuTreeList();
			return roleTreeList;
		} else {
			List<ZTreeNode> roleTreeListByUserId = this.menuService.menuTreeListByMenuIds(menuIds);
			return roleTreeListByUserId;
		}
	}

	/**
	 * 根据请求的父级菜单编号设置pcode和层级
	 */
	private void menuSetPcode(@Valid Menu menu) {
		if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
			menu.setPcode("0");
			menu.setLevels(1);
		} else {
			int code = Integer.parseInt(menu.getPcode());
			Menu pMenu = menuService.selectById(code);
			Integer pLevels = pMenu.getLevels();
			menu.setPcode(pMenu.getCode());
			menu.setLevels(pLevels + 1);
			menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
		}
	}

}
