package com.sv.eduorder.client;

import com.sv.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    // Get member info by member id for member
    @PostMapping("/educenter/member/getMemberInfoOrder/{id}")
    public UcenterMemberOrder getMemberInfoOrder(@PathVariable("id") String id);

}
