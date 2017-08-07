package com.source.app.router;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.source.base.router.BaseRouter;


@Controller
@RequestMapping(value = "{admin.path}")
public class AdminRouter extends BaseRouter{

//    @Autowired
//    private IResourceService resourceService;

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

//        UserResponse user = getSessionUser(request);
//        if (user != null && !Utils.isEmpty(user.getUsername())) {
//            String username = user.getUsername();
//            //查询我的菜单
//            List<ResourceResponse> responses = resourceService.getResourceByUsername(username, "1");
//
//            model.addAttribute("menus", responses);
//        }
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
