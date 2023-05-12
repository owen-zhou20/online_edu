package com.sv.aclservice.service.impl;

import com.sv.aclservice.entity.Role;
import com.sv.aclservice.entity.UserRole;
import com.sv.aclservice.mapper.RoleMapper;
import com.sv.aclservice.service.RoleService;
import com.sv.aclservice.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  RoleServiceImpl
 * </p>
 *
 * @author Owen
 * @since
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;


    // 7. Get roles by a user id for assign
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        // Get all roles
        List<Role> allRolesList =baseMapper.selectList(null);

        // Get roles for a user by user id
        List<UserRole> existUserRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));
        // Get all role ids from existUserRoleList
        List<String> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        //Get roleList for this user
        List<Role> assignRoles = new ArrayList<Role>();
        for (Role role : allRolesList) {
            // collect all roles if this user has
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }
        // return roleMap
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    // 8. Do assign by a user id
    @Override
    public void saveUserRoleRealtionShip(String userId, String[] roleIds) {
        // Delete all roles data about this user by user id
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userRoleService.remove(wrapper);
        // Get role list and user id and insert into table sys_user_role in DB
        List<UserRole> userRoleList = new ArrayList<>();
        for(String roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue; // pass empty roleId
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            //userRoleService.save(userRole);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    // get roleList by user id
    @Override
    public List<Role> selectRoleByUserId(String id) {
        // get roles by user id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
