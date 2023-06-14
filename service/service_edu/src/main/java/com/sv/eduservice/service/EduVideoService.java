package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * course video service
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
public interface EduVideoService extends IService<EduVideo> {

    // Delete videos by course id
    void removeVideoByCourseId(String courseId);
}
