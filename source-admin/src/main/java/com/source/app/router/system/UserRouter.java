package com.source.app.router.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.source.base.router.BaseRouter;

@Controller
@RequestMapping(value = "/user")
public class UserRouter extends BaseRouter {

	@Override
	protected String getPrefix() {
		return "/admin/sys/user";
	}



}
