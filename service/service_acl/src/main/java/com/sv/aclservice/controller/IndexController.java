package com.sv.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.sv.aclservice.entity.Permission;
import com.sv.aclservice.entity.User;
import com.sv.aclservice.service.IndexService;
import com.sv.aclservice.service.PermissionService;
import com.sv.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
Login, Logout and auth
 */
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * get current user info by token
     */
    @GetMapping("info")
    public R info(){
        //get current username from security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    /**
     * get permissionList for current user
     * @return
     */
    @GetMapping("menu")
    public R getMenu(){
        //get current username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    /**
     * logout ( TokenLogoutHandler already did logout)
     * @return
     */
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
