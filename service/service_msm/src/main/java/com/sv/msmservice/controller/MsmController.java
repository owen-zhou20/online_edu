package com.sv.msmservice.controller;

import com.sv.commonutils.R;
import com.sv.msmservice.service.MsmService;
import com.sv.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    // Send a text message use Ali cloud
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        // Get text code from redis, return if can get one
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }else {
            // Get a random code, send to Ali SMS
            code = RandomUtil.getSixBitRandom();
            Map<String, Object> param = new HashMap<>();
            param.put("code", code);
            boolean isSend = msmService.sendMsm(param, phone);
            if (isSend) {
                // If success to send a code, put code in redis
                // set 10 mins to timeout
                redisTemplate.opsForValue().set(phone,code,10, TimeUnit.MINUTES);
                return R.ok();
            } else {
                return R.error().message("Fail to send a text message!");
            }
        }
    }

    // Send a text message use Yisha
    @GetMapping("sendYisha/{phone}")
    public R sendMsmYisha(@PathVariable String phone){
        // Get a random code, send to Yisha SMS
        String code = RandomUtil.getSixBitRandom();
        boolean isSend = msmService.sendMsmYisha(code, phone);
        // If success to send a code, put code in redis
        if(isSend){
            return R.ok();
        }else {
            return R.error().message("Fail to send a text message!");
        }
    }

}
