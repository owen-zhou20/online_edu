package com.sv.eduorder.service;

import com.sv.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * Pay log service
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
public interface PayLogService extends IService<PayLog> {

    // Create a Wechat QR code for a course payment
    Map createQrcode(String orderNo);

    // Select order payment status by order No.
    Map<String, String> queryPayStatus(String orderNo);

    // Add payment record to t_pay_log table and update order payment status in t_order in database
    void updateOrderStatus(Map<String, String> map);
}
