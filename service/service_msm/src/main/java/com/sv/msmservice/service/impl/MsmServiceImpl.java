package com.sv.msmservice.service.impl;

import com.sv.msmservice.service.MsmService;
import com.sv.msmservice.utils.HttpUtils;
import com.sv.msmservice.utils.ConstantVodUtils;

import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.alibaba.fastjson.JSON;
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
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName", "我的谷粒在线教育网站");
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

    // Send a text message use Yisha
    @Override
    public boolean sendMsmYisha(String code, String phone) {
        // set 10 mins to timeout
        String host = "https://intlsms.market.alicloudapi.com";
        String path = "/comms/sms/sendmsgall";
        String method = "POST";
        String appcode = ConstantVodUtils.APPCODE;
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
        bodys.put("channel", "0");
        bodys.put("mobile", "+61"+phone);
        bodys.put("templateID", ConstantVodUtils.TEMPLATE_ID);
        bodys.put("templateParamSet", code+","+ConstantVodUtils.TIMEOUT);

        System.out.println("appcode ===>"+appcode);
        System.out.println("TEMPLATE_ID ===>"+ConstantVodUtils.TEMPLATE_ID);
        System.out.println("TIMEOUT ===>"+ConstantVodUtils.TIMEOUT);
        System.out.println("phone ===>"+phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
            /*HttpEntity entity = response.getEntity();
            String rs = JSONObject.toJSONString(entity);
            JSONObject rsObject = JSON.parseObject(rs);
            String rsCode = rsObject.getString("code");*/
            /*JSONObject rsObject = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            String rsCode = rsObject.getString("code");
            System.out.println("rsCode ===>"+rsCode);

            if("0000".equals(rsCode)){
                return true;
            } else{
                return false;
            }*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
