package com.sv.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sv.aclservice.entity.Role;
import com.sv.aclservice.entity.User;
import com.sv.aclservice.service.IndexService;
import com.sv.aclservice.service.PermissionService;
import com.sv.aclservice.service.RoleService;
import com.sv.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * get user login info(userInfo, menuList from Redis) by username
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new SvException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //get roleList by userId
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            // front-end must return a roleName，otherwise it has an error. So return empty roleName if it doesn't have a roleName.
            roleNameList.add("");
        }

        //get menuList by userId
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        //result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("avatar", "https://img0.baidu.com/it/u=1541979210,2328427362&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * get menuList by user Id
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //get menuList by userId
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }


}
