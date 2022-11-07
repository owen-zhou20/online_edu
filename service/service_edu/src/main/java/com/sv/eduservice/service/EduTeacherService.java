package com.sv.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    // pagination select teacher list
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
