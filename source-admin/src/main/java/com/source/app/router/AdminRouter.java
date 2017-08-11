package com.source.app.router;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.source.app.shiro.ShiroKit;
import com.source.base.node.MenuNode;
import com.source.base.router.BaseRouter;
import com.source.system.entity.User;
import com.source.system.service.IMenuService;
import com.source.system.service.IUserService;
import com.source.utils.BeanCopier;


@Controller
@RequestMapping(value = "{admin.path}")
public class AdminRouter extends BaseRouter{

	
	
	@Autowired
	IMenuService menuService;

	@Autowired
	IUserService userService;
	
	
    @Override
    protected String getPrefix() {
        return "/admin";
    }

    /**
     * 跳转到后台登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {//已登录
            return "/admin/index";
        }
        return "/admin/login";
    }

    /**
     * 跳转到后台首页面
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(Model model, HttpServletRequest request) {
    	 Subject subject = SecurityUtils.getSubject();
         if (!subject.isAuthenticated() && !subject.isRemembered()) {//已登录
             return new ModelAndView("/admin/login");
         }
         
         //获取菜单列表
         List<Integer> roleList = ShiroKit.getUser().getRoleList();
         List<MenuNode> menus = menuService.getMenusByRoleIds(roleList);
         List<MenuNode> titles = MenuNode.buildTitle(menus);
         
         
         List<MenuNode> menNodes = new ArrayList<MenuNode>();
         
         for (int i = 0; i < titles.size(); i++) {
        	 MenuNode menuNode = new MenuNode();
        	 
        	 menuNode =  BeanCopier.copy(titles.get(i), MenuNode.class);
        	 menuNode.setHttp(MenuNode.changeHttp(titles.get(i).getUrl()));

        	 menNodes.add(menuNode);
		}
         
         model.addAttribute("titles", menNodes);

         //获取用户头像
         Long id = ShiroKit.getUser().getId();
         User user = userService.selectById(id);
         String avatar = user.getAvatar();
         model.addAttribute("avatar", avatar);
         
        return new ModelAndView("/admin/index");
    }

    /**
     * 后台控制面板页面
     * @return
     */
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public String dashboard() {
        return "/admin/content";
    }
}
