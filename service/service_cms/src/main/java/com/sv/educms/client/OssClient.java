package com.sv.educms.client;

import com.sv.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "service-oss",fallback = OssFileDegradeFeignClient.class)
public interface OssClient {
    // delete object from Ali OSS
    @DeleteMapping("/eduoss/deleteOssFile")
    public R deleteOssFile(@RequestParam("avatar") String avatar);
}
