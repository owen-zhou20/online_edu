package com.sv.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.R;
import com.sv.eduorder.entity.Order;
import com.sv.eduorder.service.OrderService;
import com.sv.servicebase.exceptionhandler.SvException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Order controller
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Add an order by courseId and memberId. return orderNo
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(Strings.isEmpty(memberId)){
            throw new SvException(20001,"Please login!");
        }
        String orderNo = orderService.createOrders(courseId, memberId);
        return R.ok().data("orderNo",orderNo);
    }

    // 2. Get order info by order No.
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        System.out.println("order:"+order);
        return R.ok().data("item",order);
    }

    // 3. Get order status by course id and member id
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

}

