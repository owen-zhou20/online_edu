package com.sv.eduorder.service;

import com.sv.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
public interface OrderService extends IService<Order> {

    // 1. add order
    String createOrders(String courseId, String memberId);
}
