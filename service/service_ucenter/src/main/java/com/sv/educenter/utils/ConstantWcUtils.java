package com.sv.educenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantWcUtils implements InitializingBean {

    @Value("${wc.open.app_id}")
    private String appId;
    @Value("${wc.open.app_secret}")
    private String appSecret;
    @Value("${wc.open.redirect_url}")
    private String redirectUrl;

    public static String WC_OPEN_APP_ID;
    public static String WC_OPEN_APP_SECRET;
    public static String WC_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        WC_OPEN_APP_ID = appId;
        WC_OPEN_APP_SECRET = appSecret;
        WC_OPEN_REDIRECT_URL = redirectUrl;
    }
}
