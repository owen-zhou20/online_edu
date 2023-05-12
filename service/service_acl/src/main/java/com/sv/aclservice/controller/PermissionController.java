package com.sv.aclservice.controller;


import com.sv.aclservice.entity.Permission;
import com.sv.aclservice.service.PermissionService;
import com.sv.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Menu management and do assign
 *
 * @author Owen
 * @since 08-11-2022
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // Get all menus include children menus
    @ApiOperation(value = "Get all menus include children menus")
    @GetMapping
    public R indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuSv();
        return R.ok().data("children",list);
    }

    // Delete all menus by menu id include children menus
    @ApiOperation(value = "Delete all menus by menu id include children menus")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        permissionService.removeChildByIdSv(id);
        return R.ok();
    }

    // Do assign by a role id
    @ApiOperation(value = "Do assign by a role id")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShipSv(roleId, permissionId);
        return R.ok();
    }

    // Get menus by a role id for assign
    @ApiOperation(value = "Get menus by a role id for assign")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }

    // Add a menu
    @ApiOperation(value = "Add a level 1 menu")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    // Update a menu
    @ApiOperation(value = "Update a menu")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

