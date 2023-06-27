package com.sv.aclservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface IndexService {

    /**
     * get current user info by username
     * @param username
     * @return
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * get permissionList for current user
     * @param username
     * @return
     */
    List<JSONObject> getMenu(String username);

}
