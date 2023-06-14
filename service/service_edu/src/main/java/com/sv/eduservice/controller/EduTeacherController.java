package com.sv.eduservice.controller;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduTeacher;
import com.sv.eduservice.entity.vo.TeacherQuery;
import com.sv.eduservice.service.EduTeacherService;
import com.sv.servicebase.exceptionhandler.SvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * Teacher controller
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
@Api(tags = "teacher management")
@RestController
@RequestMapping("/eduservice/teacher")
//@CrossOrigin   //to fix CORS
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //1. select all teachers
    @ApiOperation(value = "Get teacher list for all teachers")
    @GetMapping("findAll")
    //@PreAuthorize("hasAuthority('teacher.list')")
    public R findAllTeacher(){
        // call MP
        List<EduTeacher> teachers = teacherService.list(null);
        return R.ok().data("teacherList",teachers);
    }

    //2. Logic delete a teacher
    @ApiOperation(value = "Logic delete a teacher")
    @DeleteMapping("{id}")
    //@PreAuthorize("hasAuthority('teacher.remove')")
    public R removeTeacher(
            @ApiParam(name = "id", value = "teacher ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error().message("Delete fail!");
        }
    }

    //3. pagination select teacher list
    @ApiOperation("Pagination select teachers")
    @GetMapping("pageTeacher/{current}/{limit}")
    //@PreAuthorize("hasAuthority('teacher.list')")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher =  new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> teacherList = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",teacherList);
    }


    //4. pagination condition select teacher list
    @ApiOperation("multiple condition select teachers with pagination")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    //@PreAuthorize("hasAuthority('teacher.list')")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){

        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        QueryWrapper wrapper = new QueryWrapper();

        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
            //System.out.println(begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
            //System.out.println(end);
        }

        //sort by create time
        wrapper.orderByDesc("gmt_modified");


        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> teacherList = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",teacherList);
    }

    //5. add a teacher
    @ApiOperation(value = "add a teacher")
    @PostMapping("addTeacher")
    //@PreAuthorize("hasAuthority('teacher.add')")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean rs = teacherService.save(eduTeacher);
        if(rs){
            return R.ok();
        }else {
            return R.error().message("Add fail!");
        }
    }

    //6. select a teacher by teacher id
    @ApiOperation(value = "select a teacher by teacher id")
    @GetMapping("getTeacher/{id}")
    //@PreAuthorize("hasAuthority('teacher.list')")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        if(!StringUtils.isEmpty(eduTeacher.getName())) {
            return R.ok().data("teacher", eduTeacher);
        }else {
            return R.error().message("Select fail!");
        }
    }

    //7. modify a teacher
    @ApiOperation(value = "modify a teacher")
    @PutMapping("updateTeacher")
    //@PreAuthorize("hasAuthority('teacher.update')")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean rs = teacherService.updateById(eduTeacher);
        if(rs){
            return R.ok();
        }else{
            return R.error().message("Update fail!");
        }
    }

}

