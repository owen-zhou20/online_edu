package com.sv.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Teacher Service
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
public interface EduTeacherService extends IService<EduTeacher> {

    // pagination select teacher list
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);

    //2. Logic delete a teacher
    boolean removeTeacher(String id);

    // Get teachers for homepage
    List<EduTeacher> getTeacherHomepage();
}
