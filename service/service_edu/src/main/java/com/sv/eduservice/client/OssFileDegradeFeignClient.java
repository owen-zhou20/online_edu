package com.sv.eduservice.client;

import com.sv.commonutils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class OssFileDegradeFeignClient implements OssClient{

    @Override
    public R deleteOssFile(String avatar){
        return R.error().message("Network error! Fail to delete this file from Ali OSS");
    }
}
