package com.source.app.constant.factory;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.source.app.state.ManagerStatus;
import com.source.app.state.MenuStatus;
import com.source.app.state.Sex;
import com.source.app.utils.SpringContextHolder;
import com.source.system.entity.Dept;
import com.source.system.entity.Menu;
import com.source.system.entity.Role;
import com.source.system.entity.User;
import com.source.system.mapper.DeptMapper;
import com.source.system.mapper.MenuMapper;
import com.source.system.mapper.RoleMapper;
import com.source.system.mapper.UserMapper;
import com.source.utils.Convert;
import com.source.utils.StrKit;
import com.source.utils.ToolUtil;

/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

	
//	
    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
//    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
    private MenuMapper menuMapper = SpringContextHolder.getBean(MenuMapper.class);
//    private NoticeMapper noticeMapper = SpringContextHolder.getBean(NoticeMapper.class);
//
    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 根据用户id获取用户名称
     */
    @Override
    public String getUserNameById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getName();
        } else {
            return "--";
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     */
    @Override
    public String getUserAccountById(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAccount();
        } else {
            return "--";
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Override
    public String getRoleName(String roleIds) {
        Integer[] roles = Convert.toIntArray(roleIds);
        StringBuilder sb = new StringBuilder();
        for (int role : roles) {
            Role roleObj = roleMapper.selectById(role);
            if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
                sb.append(roleObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 通过角色id获取角色名称
     */
    @Override
    public String getSingleRoleName(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
    public String getSingleRoleTip(Integer roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getName())) {
            return roleObj.getTips();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
    public String getDeptName(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getFullname())) {
            return dept.getFullname();
        }
        return "";
    }

    /**
     * 获取菜单的名称们(多个)
     */
    @Override
    public String getMenuNames(String menuIds) {
        Integer[] menus = Convert.toIntArray(menuIds);
        StringBuilder sb = new StringBuilder();
        for (int menu : menus) {
            Menu menuObj = menuMapper.selectById(menu);
            if (ToolUtil.isNotEmpty(menuObj) && ToolUtil.isNotEmpty(menuObj.getName())) {
                sb.append(menuObj.getName()).append(",");
            }
        }
        return StrKit.removeSuffix(sb.toString(), ",");
    }

    /**
     * 获取菜单名称
     */
    @Override
    public String getMenuName(Integer menuId) {
        if (ToolUtil.isEmpty(menuId)) {
            return "";
        } else {
            Menu menu = menuMapper.selectById(menuId);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    @Override
    public String getMenuNameByCode(String code) {
        if (ToolUtil.isEmpty(code)) {
            return "";
        } else {
            Menu param = new Menu();
            param.setCode(code);
            Menu menu = menuMapper.selectOne(param);
            if (menu == null) {
                return "";
            } else {
                return menu.getName();
            }
        }
    }

    /**
     * 获取字典名称
     */
//    @Override
//    public String getDictName(Integer dictId) {
//        if (ToolUtil.isEmpty(dictId)) {
//            return "";
//        } else {
//            Dict dict = dictMapper.selectById(dictId);
//            if (dict == null) {
//                return "";
//            } else {
//                return dict.getName();
//            }
//        }
//    }

    /**
     * 获取通知标题
     */
//    @Override
//    public String getNoticeTitle(Integer dictId) {
//        if (ToolUtil.isEmpty(dictId)) {
//            return "";
//        } else {
//            Notice notice = noticeMapper.selectById(dictId);
//            if (notice == null) {
//                return "";
//            } else {
//                return notice.getTitle();
//            }
//        }
//    }

    /**
     * 获取性别名称
     */
    @Override
    public String getSexName(Integer sex) {
        return Sex.valueOf(sex);
    }

    /**
     * 获取用户登录状态
     */
    @Override
    public String getStatusName(Integer status) {
        return ManagerStatus.valueOf(status);
    }

    /**
     * 获取菜单状态
     */
    @Override
    public String getMenuStatusName(Integer status) {
        return MenuStatus.valueOf(status);
    }


    /**
     * 查询字典
     */
//    @Override
//    public List<Dict> findInDict(Integer id) {
//        if (ToolUtil.isEmpty(id)) {
//            return null;
//        } else {
//            EntityWrapper<Dict> wrapper = new EntityWrapper<>();
//            List<Dict> dicts = dictMapper.selectList(wrapper.eq("pid", id));
//            if (dicts == null || dicts.size() == 0) {
//                return null;
//            } else {
//                return dicts;
//            }
//        }
//    }

    /**
     * 获取被缓存的对象(用户删除业务)
     */
//    @Override
//    public String getCacheObject(String para) {
//        return LogObjectHolder.me().get().toString();
//    }

}
