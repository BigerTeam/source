package com.source.app.controller.system;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.naming.NoPermissionException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.plugins.Page;
import com.source.app.controller.AdminBaseController;
import com.source.app.factory.UserFactory;
import com.source.app.shiro.ShiroKit;
import com.source.app.shiro.ShiroUser;
import com.source.app.state.ManagerStatus;
import com.source.app.wrapper.system.UserWarpper;
import com.source.base.common.Const;
import com.source.base.exception.BizExceptionEnum;
import com.source.base.exception.BussinessException;
import com.source.base.model.vo.DataGrid;
import com.source.base.tips.Tip;
import com.source.log.annotation.Log;
import com.source.system.entity.User;
import com.source.system.model.request.UserRequest;
import com.source.system.service.IUserService;
import com.source.utils.BeanCopier;
import com.source.utils.FileUtil;
import com.source.utils.ToolUtil;

/**
 * 用户前端控制器
 * 
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends AdminBaseController {

	@Autowired
	private IUserService userService;

	/**
	 * 获取管理员列表
	 * 
	 * @author zhuyangxu
	 * @data 2017年8月9日 下午9:46:56
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pages", method = RequestMethod.GET)
    @Log(module="用户模块",description="获取用户列表")
	@ResponseBody
	public DataGrid getPages(UserRequest request) {
		Page<Map<String, Object>> page = userService.getUsersPage(getPagination(request), request);
		new UserWarpper(page.getRecords()).warp();
		return super.buildDataGrid(page);
	}

	/**
	 * 修改当前用户的密码
	 */
	@RequestMapping("/changepw")
    @Log(module="用户模块",description="修改用户密码")
	@ResponseBody
	public Object changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestParam String rePwd) {
		if (!newPwd.equals(rePwd)) {
			throw new BussinessException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
		}
		long userId = ShiroKit.getUser().getId();
		User user = userService.selectById(userId);
		String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
		if (user.getPassword().equals(oldMd5)) {
			String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
			user.setPassword(newMd5);
			userService.updateById(user);
			return SUCCESS_TIP;
		} else {
			throw new BussinessException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
		}
	}

	/**
	 * 添加管理员
	 */
	@RequestMapping("/add")
    @Log(module="用户模块",description="添加管理员")
	@ResponseBody
	public Tip add(@Valid UserRequest userRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 判断账号是否重复
		User theUser = userService.getUser(userRequest.getAccount());
		if (theUser != null) {
			throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
		}
		// 完善账号信息
		userRequest.setSalt(ShiroKit.getRandomSalt(5));
		userRequest.setPassword(ShiroKit.md5(userRequest.getPassword(), userRequest.getSalt()));
		userRequest.setStatus(ManagerStatus.OK.getCode());
		userRequest.setCreatetime(new Date());
		User user = BeanCopier.copy(userRequest, User.class);
		userService.insert(user);
		return SUCCESS_TIP;
	}

	/**
	 *  修改管理员
	 * @author zhuyangxu 
	 * @data 2017年8月11日 下午3:49:31
	 * @param user
	 * @param result
	 * @return
	 * @throws NoPermissionException
	 */
	@ResponseBody
    @Log(module="用户模块",description="修改管理员")
	@RequestMapping("/edit")
	public Tip edit(@Valid UserRequest user, BindingResult result) throws NoPermissionException {
		if (result.hasErrors()) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
			userService.updateById(UserFactory.createUser(user));
			return SUCCESS_TIP;
		} else {
			ShiroUser shiroUser = ShiroKit.getUser();
			if (shiroUser.getId().equals(user.getId())) {
				userService.updateById(UserFactory.createUser(user));
				return SUCCESS_TIP;
			} else {
				throw new BussinessException(BizExceptionEnum.NO_PERMITION);
			}
		}
	}

	/**
	 * 删除管理员（逻辑删除）
	 */
	@RequestMapping("/delete")
    @Log(module="用户模块",description="删除管理员")
	@ResponseBody
	public Tip delete(@RequestParam Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能删除超级管理员
		if (userId.equals(Const.ADMIN_ID)) {
			throw new BussinessException(BizExceptionEnum.CANT_DELETE_ADMIN);
		}
		//自己不能删除自己
		if(userId.equals(ShiroKit.getUser().getId())){
			throw new BussinessException(BizExceptionEnum.CANT_DELETE_USER);
		}
		userService.setStatus(userId, ManagerStatus.DELETED.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 查看管理员详情
	 */
	@RequestMapping("/view/{userId}")
	@ResponseBody
	public User view(@PathVariable Integer userId) {
		if (ToolUtil.isEmpty(userId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		return userService.selectById(userId);
	}

	/**
	 * 重置管理员的密码
	 */
	@RequestMapping("/reset")
    @Log(module="用户模块",description="重置密码")
	@ResponseBody
	public Tip reset(@RequestParam Integer userId) {
		if (ToolUtil.isEmpty(userId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		User user = userService.selectById(userId);
		user.setSalt(ShiroKit.getRandomSalt(5));
		user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
		userService.updateById(user);
		return SUCCESS_TIP;
	}

	/**
	 * 冻结用户
	 */
	@RequestMapping("/freeze")
    @Log(module="用户模块",description="冻结账户")
	@ResponseBody
	public Tip freeze(@RequestParam Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能冻结超级管理员
		if (userId.equals(Const.ADMIN_ID)) {
			throw new BussinessException(BizExceptionEnum.CANT_FREEZE_ADMIN);
		}
		userService.setStatus(userId, ManagerStatus.FREEZED.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 解除冻结用户
	 */
	@RequestMapping("/unfreeze")
    @Log(module="用户模块",description="解除冻结")
	@ResponseBody
	public Tip unfreeze(@RequestParam Long userId) {
		if (ToolUtil.isEmpty(userId)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		userService.setStatus(userId, ManagerStatus.OK.getCode());
		return SUCCESS_TIP;
	}

	/**
	 * 分配角色
	 * 
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("/setRole")
    @Log(module="用户模块",description="分配角色")
	@ResponseBody
	public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
		if (ToolUtil.isOneEmpty(userId, roleIds)) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		// 不能修改超级管理员
		if (userId.equals(Const.ADMIN_ID)) {
			throw new BussinessException(BizExceptionEnum.CANT_CHANGE_ADMIN);
		}
		this.userService.setRoles(userId, roleIds);
		return SUCCESS_TIP;
	}

	/**
	 * 上传图片
	 * 
	 * @author zhuyangxu
	 * @data 2017年8月11日 下午3:23:56
	 * @param picture
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/upload")
	@ResponseBody
	public String upload(@RequestPart("file") MultipartFile picture) {
		String pictureName = UUID.randomUUID().toString() + ".jpg";
		Calendar cd = Calendar.getInstance();
		int year = cd.get(Calendar.YEAR);
		String month = addZero(cd.get(Calendar.MONTH) + 1);
		String day = addZero(cd.get(Calendar.DAY_OF_MONTH));
		pictureName = year + "_" + month + "_" + day + "_" + UUID.randomUUID().toString().replace("-", "") + ".jpg";
		try {
			String fileSavePath = getFileUploadPath(pictureName);
			picture.transferTo(new File(fileSavePath + pictureName));
		} catch (Exception e) {
		}
		return pictureName;

	}

	public static String addZero(int i) {
		if (i < 10) {
			String tmpString = "0" + i;
			return tmpString;
		} else {
			return String.valueOf(i);
		}

	}

	/**
	 * 获取上传图片的地址
	 * 
	 * @author zhuyangxu
	 * @data 2017年7月7日 下午3:34:11
	 * @return
	 */
	public static String getFileUploadPath(String fileID) {
		String fileUploadPath = "/Users/admin/images";
		// 如果没有写文件上传路径,保存到临时目录
		if (null == fileUploadPath || "".equals(fileUploadPath)) {
			return getTempPath();
		} else {
			String[] split = fileID.split("_");
			if (split.length > 3) {
				String year = split[0];
				String month = split[1];
				String day = split[2];
				if (!fileUploadPath.endsWith(File.separator)) {
					fileUploadPath = fileUploadPath + File.separator;
				}
				fileUploadPath = fileUploadPath + year + File.separator + month + File.separator + day;
			}
			// 判断有没有结尾符,没有得加上
			if (!fileUploadPath.endsWith(File.separator)) {
				fileUploadPath = fileUploadPath + File.separator;
			}
			// 判断目录存不存在,不存在得加上
			File file = new File(fileUploadPath);
			file.mkdirs();
			return fileUploadPath;
		}

	}

	/**
	 * 获取临时路径
	 * 
	 * @author zhuyangxu
	 * @data 2017年8月11日 下午3:33:32
	 * @return
	 */
	public static String getTempPath() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * @author zhuyangxu
	 * @data 2017年8月11日 下午3:33:06
	 * @param pictureId
	 * @param response
	 */
	@RequestMapping("/kaptcha/{pictureId}")
	public void renderPicture(@PathVariable("pictureId") String pictureId, HttpServletResponse response) {
		String path = getFileUploadPath(pictureId) + pictureId + ".jpg";
		try {
			byte[] bytes = FileUtil.toByteArray(path);
			response.getOutputStream().write(bytes);
		} catch (Exception e) {
			// 如果找不到图片就返回一个默认图片
			try {
				response.sendRedirect("/static/img/a1.jpg");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
