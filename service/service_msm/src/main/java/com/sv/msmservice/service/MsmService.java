package com.sv.msmservice.service;

import java.util.Map;

public interface MsmService {

    // Send a text message use Ali cloud
    boolean sendMsm(Map<String, Object> param, String phone);

    // Send a text message use Yisha
    // Get a random code, send to Yisha SMS
    boolean sendMsmYisha(String code, String phone);
}
