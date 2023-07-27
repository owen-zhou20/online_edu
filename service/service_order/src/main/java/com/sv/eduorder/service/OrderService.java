package com.sv.eduorder.service;

import com.sv.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * Order service
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
public interface OrderService extends IService<Order> {

    // 1. Add an order by courseId and memberId. return orderNo
    String createOrders(String courseId, String memberId);
}
