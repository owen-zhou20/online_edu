package com.sv.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.eduservice.client.VodClient;
import com.sv.eduservice.entity.EduVideo;
import com.sv.eduservice.mapper.EduVideoMapper;
import com.sv.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * course video serviceImpl
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // VodClient
    @Autowired
    private VodClient vodClient;

    // Delete videos by course id
    @Override
    public void removeVideoByCourseId(String courseId) {
        // Delete vod videos
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        // List<EduVideo>  to List<String>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                videoIds.add(eduVideo.getVideoSourceId());
            }
        }
        // batch delete from vod
        if(videoIds.size() > 0){
            // Delete videos by videoIds
            vodClient.deleteBatch(videoIds);
        }

        // Delete videos in DB
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
