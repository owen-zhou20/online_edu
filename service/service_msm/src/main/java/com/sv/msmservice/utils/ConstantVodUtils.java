package com.sv.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    // read configuration file
    @Value("${yisha.templateID}")
    private String templateID;

    @Value("${yisha.timeout}")
    private String timeout;

    @Value("${yisha.appcode}")
    private String appcode;

    public static String TEMPLATE_ID;

    public static String TIMEOUT;

    public static String APPCODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        TEMPLATE_ID = templateID;
        TIMEOUT = timeout;
        APPCODE = appcode;
    }
}