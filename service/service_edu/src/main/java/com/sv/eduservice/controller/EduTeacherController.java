package com.sv.eduservice.controller;


import com.sv.eduservice.entity.EduTeacher;
import com.sv.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //restful select all teachers
    @GetMapping("findAll")
    public List<EduTeacher> findAllTeacher(){
        //call service methods to find
        List<EduTeacher> teachers = teacherService.list(null);
        return teachers;
    }

    //2. Logic delete teacher
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        boolean flag = teacherService.removeById(id);
        return flag;
    }


}

