package com.sv.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    // 1. Upload video to Ali Vod
    String uploadVideoAliVod(MultipartFile file);

    // 2. Delete a video from Ali VOD
    void removeAliVodVideo(String id);

    // 3. Batch delete videos
    void removeMoreAliVodVideo(List videoIdList);


}
