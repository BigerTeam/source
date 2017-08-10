package com.source.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.source.system.entity.User;
import com.source.system.model.request.UserRequest;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zhuyangxu
 * @since 2017-07-17
 */
public interface UserMapper extends BaseMapper<User> {
	

    /**
     * 修改用户状态
     *
     * @param user
     */
    int setStatus(@Param("userId") Integer userId, @Param("status") int status);

    /**
     * 修改密码
     *
     * @param userId
     * @param pwd
     */
    int changePwd(@Param("userId") Integer userId, @Param("pwd") String pwd);

    /**
     * 根据条件查询用户列表
     *
     * @return
     */
    List<Map<String, Object>> selectUsers(Pagination page,@Param("name") String name,@Param("gender") int gender,@Param("mobile") String mobile, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);

    /**
     * 设置用户的角色
     *
     * @return
     */
    int setRoles(@Param("userId") Integer userId, @Param("roleIds") String roleIds);

    /**
     * 通过账号获取用户
     *
     * @param account
     * @return
     */
    User getByAccount(@Param("account") String account);
    
    
    /**
     * 根据查询条件获取用户集合
     * @author zhuyangxu 
     * @data 2017年8月7日 下午5:26:25
     * @param name
     * @param beginTime
     * @param endTime
     * @param deptid
     * @return
     */
    List<User> selectUsersByDeptid(Pagination page,@Param("name") String name, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("deptid") Integer deptid);
 
    
    public List<User> findUser(Pagination page, @Param("request") UserRequest request);

    

}