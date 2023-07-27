package com.sv.educms.client;

import com.sv.commonutils.R;
import org.springframework.stereotype.Component;

@Component
public class OssFileDegradeFeignClient implements OssClient {

    @Override
    public R deleteOssFile(String id) {
        return R.error().message("Network error! Fail to delete this file form Ali OSS");
    }
}
