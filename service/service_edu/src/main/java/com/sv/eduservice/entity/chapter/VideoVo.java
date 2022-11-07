package com.sv.eduservice.entity.chapter;

import lombok.Data;

@Data
public class VideoVo {

    private String id;

    private String title;

    // video id in Ali oss
    private String videoSourceId;
}
