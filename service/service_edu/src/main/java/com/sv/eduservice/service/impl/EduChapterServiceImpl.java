package com.sv.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.eduservice.entity.EduChapter;
import com.sv.eduservice.entity.EduVideo;
import com.sv.eduservice.entity.chapter.ChapterVo;
import com.sv.eduservice.entity.chapter.VideoVo;
import com.sv.eduservice.mapper.EduChapterMapper;
import com.sv.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.eduservice.service.EduVideoService;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Course chapter serviceImpl
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    // Get all course chapters list include nested videos list by course id
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1. Get all course chapters list by course id
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        wrapperChapter.orderByAsc("sort","id");
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2. Get all course videos list by course id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.orderByAsc("sort","id");
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // Final list
        List<ChapterVo> finalList = new ArrayList<>();

        // 3. Put all course chapters list into final list
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            // 4. Put all course videos list base each chapter into final list
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            finalList.add(chapterVo);
        }

        return finalList;
    }

    // Delect chapter by id
    @Override
    public boolean deleteChapter(String chapterId) {
        // Select chapter id in edu_video table in DB. Don't delete this chapter if get any data
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count > 0){ // Don't delete this chapter if can get videos.
            throw new SvException(20001,"Can't delete this chapter if this chapter has any video");
        }else{ // Delete this chapter if can't get videos.
            // Delete this chapter
            int rs = baseMapper.deleteById(chapterId);
            return rs>0;
        }

    }

    // Delete chapters by course id
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
