package com.source.system.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.source.base.node.ZTreeNode;
import com.source.base.service.impl.BaseService;
import com.source.system.entity.Relation;
import com.source.system.entity.Role;
import com.source.system.mapper.RelationMapper;
import com.source.system.mapper.RoleMapper;
import com.source.system.service.IRoleService;
import com.source.utils.Convert;

/**
 *  服务实现类
 * @author zhuyangxu
 * @since 2017-07-17
 */
@Service
public class RoleServiceImpl extends BaseService<RoleMapper, Role> implements IRoleService {

	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	RelationMapper relationMapper;
	
	@Override
	public List<Map<String, Object>> selectRoles(String condition) {
		return roleMapper.selectRoles(condition);
	}

	@Override
	public int deleteRolesById(Integer roleId) {
		return roleMapper.deleteRolesById(roleId);
	}

	@Override
	public List<ZTreeNode> roleTreeList() {
		return roleMapper.roleTreeList();
	}

	@Override
	public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
		return roleMapper.roleTreeListByRoleId(roleId);
	}

	@Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {
        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
        // 添加新的权限
        for (Integer id : Convert.toIntArray(ids)) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);
        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
    }
}
