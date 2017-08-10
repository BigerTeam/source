package com.source.app.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.source.app.shiro.ShiroKit;
import com.source.base.exception.ApplicationException;
import com.source.base.exception.StatusCode;
import com.source.system.exception.SystemError;
import com.source.system.model.response.UserResponse;
import com.source.system.service.IUserService;

/**
 * Function:登录控制器. <br/>
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private static Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private IUserService userService;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "captcha", required = false) String captcha) {
        //验证码校验
        String c = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        System.out.println("验证码:" + c);
        if (c != null && !c.equalsIgnoreCase(captcha)) {
            throw new ApplicationException(SystemError.CAPTCHA_ERROR.getCode(), SystemError.CAPTCHA_ERROR.getMessage());
        }

        //用户名密码验证
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, false);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            List<Integer> roleList = ShiroKit.getUser().getRoleList();
            if(roleList == null || roleList.size() == 0){
                ShiroKit.getSubject().logout();
                throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), "该用户没有角色，无法登陆");
            }
            
        } catch (IncorrectCredentialsException e) {
            //用户名或密码错误
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(),"用户名或密码错误");
        } catch (ExcessiveAttemptsException e) {
            logger.error("登录失败{}", e.getMessage());//登录失败次数过多
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(),"登录失败次数过多");

        } catch (LockedAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已被锁定
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(),"帐号已被锁定");

        } catch (DisabledAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已被禁用
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(),"帐号已被禁用");

        } catch (ExpiredCredentialsException e) {
            logger.error("登录失败{}", e.getMessage());//帐号已过期
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), "帐号已过期");
        } catch (UnknownAccountException e) {
            logger.error("登录失败{}", e.getMessage());//帐号不存在
            throw new ApplicationException(StatusCode.NOT_FOUND.getCode(), "帐号不存在");
        } catch (UnauthorizedException e) {
            logger.error("登录失败{}", e.getMessage());//您没有得到相应的授权
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), "您没有得到相应的授权");

        }catch(ApplicationException e){
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), "该用户没有角色，无法登陆");

        } catch (Exception e) {
            logger.error("登录失败{}", e.getMessage());
            throw new ApplicationException(SystemError.LOGIN_FAILED.getCode(), "未知错误");

        }

        return userService.get(username);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/admin/login";
    }

}
