package com.sv.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // Upload video to Ali cloud Vod
    String uploadVideoAliVod(MultipartFile file);

    // Batch delete videos
    void removeMoreAliVodVideo(List videoIdList);
}
