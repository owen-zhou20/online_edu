package com.sv.eduservice.client;

import com.sv.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
//@Component
public interface VodClient {
    // Delete a upload video from Ali VOD
    @DeleteMapping("/eduvod/video/removeAliVodVideo/{id}")
    public R removeAliVodVideo(@PathVariable("id") String id);

    // Batch delete videos
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);


}
