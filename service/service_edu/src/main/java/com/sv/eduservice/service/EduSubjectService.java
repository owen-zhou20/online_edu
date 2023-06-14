package com.sv.eduservice.service;

import com.sv.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sv.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * course subject controller
 * </p>
 *
 * @author Owen
 * @since 2022-07-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    // add course subject
    // get the upload excel file and read this excel file
    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    //course subject list(tree data)
    List<OneSubject> getAllOneTwoSubject();
}
