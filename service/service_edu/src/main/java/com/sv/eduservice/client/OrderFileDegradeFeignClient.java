package com.sv.eduservice.client;

import com.sv.commonutils.R;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class OrderFileDegradeFeignClient implements OrdersClient{

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new SvException(20001,"Network error! Fail to get isBuyCourse info from Service Order!");
    }
}
