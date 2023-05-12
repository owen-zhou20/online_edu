package com.sv.aclservice.controller;


import com.sv.aclservice.entity.User;
import com.sv.aclservice.service.RoleService;
import com.sv.aclservice.service.UserService;
import com.sv.commonutils.MD5;
import com.sv.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.aclvo.userQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * User management and do assign
 * </p>
 *
 * @author Owen
 * @since 2022-12-14
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // 1. Pagination condition select user list
    @ApiOperation(value = "Pagination condition select user list")
    //@PreAuthorize("hasAuthority('user.list')")
    @GetMapping("{page}/{limit}")
    public R getPageList(
            @ApiParam(name = "page", value = "current page", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "page limit", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "SearchObj", required = false)
            userQueryVo userQueryVo) {
        // page entity
        Page<User> pageParam = new Page<>(page, limit);
        // condition
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }
        //userService.page(pageParam, wrapper)
        // user service to get result
        IPage<User> pageModel = userService.selectPage(pageParam, wrapper);
        // return result
        System.out.println("size"+pageModel.getSize());
        return R.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    // 2. Get a user by user id
    @ApiOperation(value = "Get a user by user id")
    @GetMapping("get/{id}")
    public R getById(@PathVariable String id){
        User user = userService.getById(id);
        System.out.println("user====>"+user.toString());
        return R.ok().data("item",user);
    }

    // 3. Add a user
    @ApiOperation(value = "Save a user")
    //@PreAuthorize("hasAuthority('user.add')")
    @PostMapping("save")
    public R save(@RequestBody User user) {
        // Use MD5 to encrypt the password for the user
        String encrypt = MD5.encrypt(user.getPassword());
        user.setPassword(encrypt);

        boolean isSuccess = userService.save(user);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 4. Update a user by user id
    @ApiOperation(value = "Update a user by user id")
    //@PreAuthorize("hasAuthority('user.update')")
    @PutMapping("update")
    public R updateById(@RequestBody User user) {
        boolean isSuccess = userService.updateById(user);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 5. Delete a user by user id
    @ApiOperation(value = "Delete a user by user id")
    //@PreAuthorize("hasAuthority('user.remove')")
    @DeleteMapping("remove/{id}")
    public R removeById(@PathVariable String id) {
        boolean isSuccess = userService.removeById(id);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 6. Batch delete users by ids
    @ApiOperation(value = "Batch delete users by ids")
    //@PreAuthorize("hasAuthority('user.remove')")
    @DeleteMapping("batchRemove")
    public R batchRemove(@RequestBody List<String> idList) {
        boolean isSuccess = userService.removeByIds(idList);
        if(isSuccess) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    // 7. Get roles by a user id for assign
    @ApiOperation(value = "Get roles by a user id for assign")
    //@PreAuthorize("hasAuthority('user.assgin')")
    @GetMapping("toAssign/{userId}")
    public R toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return R.ok().data(roleMap);
    }

    // 8. Do assign by a user id
    @ApiOperation(value = "Do assign by a user id")
    //@PreAuthorize("hasAuthority('user.assgin')")
    @PostMapping("doAssign")
    public R doAssign(@RequestParam String userId,@RequestParam String[] roleIds) {
        roleService.saveUserRoleRealtionShip(userId,roleIds);
        return R.ok();
    }
}

