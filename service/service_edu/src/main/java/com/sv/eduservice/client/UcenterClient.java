package com.sv.eduservice.client;

import com.sv.commonutils.ordervo.UcenterMemberComment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterFileDegradeFeignClient.class)
public interface UcenterClient {
    // Get member info by member id for comment
    @PostMapping("/educenter/member/getMemberInfoComment/{id}")
    public UcenterMemberComment getMemberInfoComment(@PathVariable("id") String id);

}
