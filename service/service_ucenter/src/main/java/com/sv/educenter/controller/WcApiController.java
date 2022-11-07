package com.sv.educenter.controller;


import com.google.gson.Gson;
import com.sv.commonutils.JwtUtils;
import com.sv.educenter.entity.UcenterMember;
import com.sv.educenter.service.UcenterMemberService;
import com.sv.educenter.utils.ConstantWcUtils;
import com.sv.educenter.utils.HttpClientUtils;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller// Not @RestController
@RequestMapping("/api/ucenter/wx") // must use /api/ucenter/wx
// base Wechat OAuth 2.0
public class WcApiController {

    @Autowired
    private UcenterMemberService memberService;

    // 1. Get Wechat QR code
    @GetMapping("login")
    public String getWcCode(){
        // Wechat baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                            "?appid=%s" +
                            "&redirect_uri=%s" +
                            "&response_type=code" +
                            "&scope=snsapi_login" +
                            "&state=%s" +
                            "#wechat_redirect";

        // encode url as request
        String redirectUrl = ConstantWcUtils.WC_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new SvException(20001, e.getMessage());
        }

        // Get QR code url
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWcUtils.WC_OPEN_APP_ID,
                redirectUrl,
                "svedu"
        );

        // redirect to Wechat url to get QR code
        return "redirect:" + qrcodeUrl;
    }

    // 2. Callback
    @GetMapping("callback")
    public String callback(String code, String state){

        try {
            // 1. Get temporary code
            System.out.println("code =>"+code);
            System.out.println("state =>"+state);

            // 2. Get accsess_token and openid use temporary code
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWcUtils.WC_OPEN_APP_ID,
                    ConstantWcUtils.WC_OPEN_APP_SECRET,
                    code
            );
            // Use httpclient go to url to get accsess_token and openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo =>"+accessTokenInfo);
            // Change Json String to map use Gson
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String)mapAccessToken.get("access_token");
            String openid = (String)mapAccessToken.get("openid");

            // Register userInfo to DB
            // Check openid in DB, do not add this userInfo if this openid already exist.
            UcenterMember member = memberService.getOpenIdMember(openid);

            if(member == null){ // Add this userInfo into DB if member is empty

                // 3. Go to Wechat url to get userInfo use access_token and openid
                // Go to Wechat baseUserInfoUrl to get userInfo
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo =>"+userInfo);
                // Get user info from String userInfo use Gson
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String)userInfoMap.get("nickname"); //nickname
                String headimgurl = (String)userInfoMap.get("headimgurl"); //avatar

                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }

            // Get token by member user jwt
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            // send token to homepage
            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new SvException(20001, e.getMessage());
        }
    }

}
