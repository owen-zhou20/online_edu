package com.sv.eduservice.controller;


import com.sv.commonutils.R;
import com.sv.eduservice.entity.subject.OneSubject;
import com.sv.eduservice.service.EduSubjectService;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-07-28
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    // add course subject
    // get the upload excel file and read this excel file
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //
            subjectService.saveSubject(file,subjectService);

        return R.ok();
    }

    // course subject list tree
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = subjectService.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }


}

