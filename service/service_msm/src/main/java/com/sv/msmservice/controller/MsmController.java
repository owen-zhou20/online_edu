package com.sv.msmservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sv.commonutils.R;
import com.sv.msmservice.service.MsmService;
import com.sv.msmservice.utils.ConstantMsmUtils;
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
//@CrossOrigin
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
    public R sendMsmYisha(@PathVariable String phone) {
        // Get text code from redis, return if can get one
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        } else {
            // Get a random code, send to Yisha SMS
            code = RandomUtil.getSixBitRandom();
            boolean isSend = msmService.sendMsmYisha(code, phone);
            // If success to send a code, put code in redis
            if (isSend) {
                // If success to send a code, put code in redis
                // set 10 mins to timeout
                long timeout = Long.valueOf(ConstantMsmUtils.TIMEOUT);
                redisTemplate.opsForValue().set(phone,code, timeout, TimeUnit.MINUTES);
                return R.ok();
            } else {
                return R.error().message("Fail to send a text message!");
            }
        }
    }

    /**
     * Callback response
     {
     "reference_id":"2340629171104904100", -- reference_id
     "price":"0.05", -- price
     "mobile":"xxxxxxx", -- phone No.
     "extenal_id":"860099f54cbe4f74a20a0c63b0ea028e", -- extenal_id
     "status":{ -- status
     "updated_on":1594786353213, -- time
     "code":"200" -- code :https://openali.esandcloud.com/shortmsg/log#
     }
     }
     */
    @PostMapping("sendYisha/notify")
    public R msmYishaCallback(@RequestBody String reqMsg){
        JSONObject rsObject = JSON.parseObject(reqMsg);
        String rsStatus = rsObject.getString("status");
        String rsCode = rsObject.getString("code");
        System.out.println("rsStatus===>"+rsStatus);
        System.out.println("rsCode===>"+rsCode);
        if("200".equals(rsCode)){
            return R.ok();
        } else {
            return R.error().message("Fail to send a text message!");
        }
    }

}
