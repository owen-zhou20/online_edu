package com.sv.aclservice.service;

import com.sv.aclservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  RoleService
 * </p>
 *
 * @author Owen
 * @since
 */
public interface RoleService extends IService<Role> {

    // 7. Get roles by a user id for assign
    Map<String, Object> findRoleByUserId(String userId);

    // 8. Do assign by a user id
    void saveUserRoleRealtionShip(String userId, String[] roleId);

    // get roleList by user id
    List<Role> selectRoleByUserId(String id);
}
