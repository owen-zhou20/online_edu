package com.sv.eduservice.client;

import com.sv.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeAliVodVideo(String id) {
        return R.error().message("Network error! Fail to delete this video form Ali VOD");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("Network error! Fail to delete videos form Ali VOD");
    }
}
