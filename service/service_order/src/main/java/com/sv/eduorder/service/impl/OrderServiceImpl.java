package com.sv.eduorder.service.impl;

import com.sv.commonutils.ordervo.CourseWebVoOrder;
import com.sv.commonutils.ordervo.UcenterMemberOrder;
import com.sv.eduorder.client.EduClient;
import com.sv.eduorder.client.UcenterClient;
import com.sv.eduorder.entity.Order;
import com.sv.eduorder.mapper.OrderMapper;
import com.sv.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    // 1. add order
    @Override
    public String createOrders(String courseId, String memberId) {
        // user openfeign get member info by member id
        UcenterMemberOrder memberInfoOrder = ucenterClient.getMemberInfoOrder(memberId);

        // user openfeign get course info by course id
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        System.out.println("OrderNo:"+order.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherId(courseInfoOrder.getTeacherId());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        System.out.println("TeacherName:"+courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfoOrder.getMobile());
        order.setNickname(memberInfoOrder.getNickname());
        order.setStatus(0);  // order pay status: 0: Not pay, 1: paid
        order.setPayType(1); // WeChat
        System.out.println("order =====>"+order);
        baseMapper.insert(order);

        return order.getOrderNo();
    }



}
