package com.sv.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.sv.eduorder.entity.Order;
import com.sv.eduorder.entity.PayLog;
import com.sv.eduorder.mapper.PayLogMapper;
import com.sv.eduorder.service.OrderService;
import com.sv.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.eduorder.utils.HttpClient;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-10-07
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    // Create a Wechat QR code for a course payment
    @Override
    public Map createQrcode(String orderNo) {
        try{
            // 1. Get order info by order No
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);

            // 2. Set QR code arguments into map
            Map<String, String> map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954"); //appid
            map.put("mch_id", "1558950191"); //partner
            map.put("nonce_str", WXPayUtil.generateNonceStr()); // random
            map.put("body", order.getCourseTitle()); // Title to show: course title
            map.put("out_trade_no", orderNo); // order No.
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+""); // course price, String
            map.put("spbill_create_ip", "127.0.0.1"); // IP address
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n"); //notifyurl
            map.put("trade_type", "NATIVE"); // payment type

            // 3.  Send a httpclient request with xml arguments to Wechat url
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            // set Xml arguments
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true); // support Https
            // send http request by post
            client.post();
            // 4. Get return result from client
            // result is a xml
            String xml = client.getContent();
            // xml to map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            // returnMap for return
            Map returnMap = new HashMap<>();
            returnMap.put("out_trade_no", orderNo);
            returnMap.put("course_id", order.getCourseId());
            returnMap.put("total_fee", order.getTotalFee());
            returnMap.put("result_code", resultMap.get("result_code")); // status code in result QR code
            returnMap.put("code_url", resultMap.get("code_url"));  // result QR code address
            return returnMap;

        }catch(Exception e){
            throw new SvException(20001, "Fail to create QR code for order payment! ");
        }

    }

    // Select order payment status by order No.
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try{
            // 1. put arguments in map
            Map map = new HashMap<>();
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("out_trade_no", orderNo);
            map.put("nonce_str", WXPayUtil.generateNonceStr());

            // 2. send httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            // 3. Get client return
            String xml = client.getContent();
            // 4. change xml to map and return
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;

        }catch (Exception e){
            throw new SvException(20001,"Fail to get queryPayStatus callback");
        }

    }

    // Add payment record to t_pay_log table and update order payment status in t_order in database
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        // Get orderNo by map
        String orderNo = map.get("out_trade_no");
        // Get order info by orderNo
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        // update order status in t-order table in database
        if(order.getStatus().intValue() == 1){return;}

        order.setStatus(1); // 1: paid
        orderService.updateById(order);

        // Add this payment record in to t_pay_log table
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date()); //payment time
        payLog.setPayType(1);// payment type: 1: WeChat
        payLog.setTotalFee(order.getTotalFee());// total amount (RMB cent)
        payLog.setTradeState(map.get("trade_state"));// payment status
        payLog.setTransactionId(map.get("transaction_id")); // payment transaction No.
        payLog.setAttr(JSONObject.toJSONString(map)); // other attr in map
        baseMapper.insert(payLog);// add this payment record in to t_pay_log table
        return;


    }
}
