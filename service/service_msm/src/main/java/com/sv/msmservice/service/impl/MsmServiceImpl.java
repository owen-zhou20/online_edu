package com.sv.msmservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.msmservice.service.MsmService;
import com.sv.msmservice.utils.HttpUtils;
import com.sv.msmservice.utils.ConstantMsmUtils;

import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;


import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {


    private String templateID;

    // Send a text message use Ali cloud
    @Override
    public boolean sendMsm(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIq6nIPY09VROj", "FQ7UcixT9wEqMv9F35nORPqKr8XkTF");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        //request.setMethod(MethodType.POST);
        //request.setDomain("dysmsapi.aliyuncs.com");
        //request.setVersion("2017-05-25");
        //request.setAction("SendSms");
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "My Sv Edu Online");
        request.putQueryParameter("TemplateCode", "templateCode");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Send a text message use Yisha from Ali cloud market
    @Override
    public boolean sendMsmYisha(String code, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        // set 10 mins to timeout
        String host = "https://intlsms.market.alicloudapi.com";
        String path = "/comms/sms/sendmsgall";
        String method = "POST";
        String appcode = ConstantMsmUtils.APPCODE;
        Map<String, String> headers = new HashMap<String, String>();
        //header e.g., Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        //bodys.put("callbackUrl", "http://localhost:8222//edumsm/msm/sendYisha/notify");
        bodys.put("channel", "0");
        bodys.put("mobile", "+61"+phone);
        bodys.put("templateID", ConstantMsmUtils.TEMPLATE_ID);
        bodys.put("templateParamSet", code+","+ ConstantMsmUtils.TIMEOUT);

        System.out.println("appcode ===>"+appcode);
        System.out.println("TEMPLATE_ID ===>"+ ConstantMsmUtils.TEMPLATE_ID);
        System.out.println("TIMEOUT ===>"+ ConstantMsmUtils.TIMEOUT);
        System.out.println("phone ===>"+phone);

        try {
            /**
             * HttpUtils from
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             *
             * e.g.
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            // Get code from response
            String json=EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper(); // Jackson
            JsonNode root = mapper.readTree(json);
            System.out.println("root===>"+root.toString());
            String rsCode = root.findValue("code").asText();

            System.out.println("rsCode===>"+rsCode);
            // check code == "0000"
            if("0000".equals(rsCode)){
                return true;
            } else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
