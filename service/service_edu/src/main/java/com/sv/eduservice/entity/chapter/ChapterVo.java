package com.sv.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    // Video vo list in chapter vo
    private List<VideoVo> children = new ArrayList<>();

}
