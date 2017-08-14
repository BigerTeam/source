package com.source.app.router.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.source.base.router.BaseRouter;

@Controller
@RequestMapping(value = "/admin/log")
public class LogRouter extends BaseRouter{

    @Override
    protected String getPrefix() {
        return "/admin/sys/log";
    }
}
