package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-07-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    // course subject list tree
    List<OneSubject> getAllOneTwoSubject();
}
