package com.source.app.router.system;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.source.app.constant.factory.ConstantFactory;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.router.BaseRouter;
import com.source.system.entity.Menu;
import com.source.system.service.IMenuService;
import com.source.utils.BeanKit;
import com.source.utils.ToolUtil;

@Controller
@RequestMapping(value = "/menu")
public class MenuRouter extends BaseRouter {

	@Override
	protected String getPrefix() {
		return "/admin/sys/menu";
	}

	
	@Resource
	IMenuService menuService;

	/**
	 * 跳转到菜单详情列表页面
	 */
	@RequestMapping(value = "/menu_edit/{menuId}")
	public String menuEdit(@PathVariable Integer menuId, Model model) {
		if (ToolUtil.isEmpty(menuId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		Menu menu = this.menuService.selectById(menuId);
		// 获取父级菜单的id
		Menu temp = new Menu();
		temp.setCode(menu.getPcode());
		Wrapper<Menu> wrapper = new EntityWrapper<>(temp);
		Menu pMenu = menuService.selectOne(wrapper);
		// 如果父级是顶级菜单
		if (pMenu == null) {
			menu.setPcode("0");
		} else {
			// 设置父级菜单的code为父级菜单的id
			menu.setPcode(String.valueOf(pMenu.getId()));
		}
		Map<String, Object> menuMap = BeanKit.beanToMap(menu);
		menuMap.put("pcodeName", ConstantFactory.me().getMenuNameByCode(temp.getCode()));
		model.addAttribute("menu", menuMap);
		// LogObjectHolder.me().set(menu);
		return EDIT;
	}
}
