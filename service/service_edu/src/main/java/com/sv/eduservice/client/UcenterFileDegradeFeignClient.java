package com.sv.eduservice.client;

import com.sv.commonutils.ordervo.UcenterMemberComment;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {

    @Override
    public UcenterMemberComment getMemberInfoComment(String id) {
        throw new SvException(20001,"Network error! Fail to get member info from Ucenter member!");
    }
}
