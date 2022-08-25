package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
public interface EduChapterService extends IService<EduChapter> {

    // Get all course chapters list include all videos list by course id
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    // Delect chapter by id
    boolean deleteChapter(String chapterId);
}
