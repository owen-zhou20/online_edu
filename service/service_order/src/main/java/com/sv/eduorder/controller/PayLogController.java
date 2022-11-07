package com.sv.eduorder.controller;


import com.sv.commonutils.R;
import com.sv.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    // Create a Wechat QR code for a course payment
    @GetMapping("createQrcode/{orderNo}")
    public R createQrcode(@PathVariable String orderNo){
        Map map = payLogService.createQrcode(orderNo);
        System.out.println("Return QR code map ===>"+map);
        return R.ok().data(map);
    }

    // Get order payment status by order No.
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("Select order status map ===>"+map);
        if(map == null){
            return R.error().message("Fail to pay this order! ");
        }
        // if map is not null, get payment status by map
        if(map.get("trade_state").equals("SUCCESS")){ // success to pay
            // Add payment record to t_pay_log table and update order payment status in t_order
            payLogService.updateOrderStatus(map);
            return R.ok().message("Success to pay!");
        }
            return R.ok().code(25000).message("Waiting payment to finish!");

    }

}

