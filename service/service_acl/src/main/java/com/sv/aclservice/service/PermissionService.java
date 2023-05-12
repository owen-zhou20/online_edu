package com.sv.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.sv.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * menu service
 * </p>
 *
 * @author
 * @since
 */
public interface PermissionService extends IService<Permission> {

    // get menus by role id
    List<Permission> selectAllMenu(String roleId);

    // get assign menus by user id
    List<String> selectPermissionValueByUserId(String id);

    // get assign menus by user id (Json version)
    List<JSONObject> selectPermissionByUserId(String id);

    // Get all menus include children menus
    List<Permission> queryAllMenuSv();

    // Delete all permissions by id include children permissions
    void removeChildByIdSv(String id);

    // Give permissions to role by id
    void saveRolePermissionRealtionShipSv(String roleId, String[] permissionId);
}
