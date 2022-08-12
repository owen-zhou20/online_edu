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
 * 讲师 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
@Api(tags = "teacher management")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin   //to fix CORS
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //restful select all teachers
    @ApiOperation(value = "List for all teachers")
    @GetMapping("findAll")
    public R findAllTeacher(){
        //call service methods to find
        List<EduTeacher> teachers = teacherService.list(null);
        return R.ok().data("teacherList",teachers);
    }

    //2. Logic delete a teacher
    @ApiOperation(value = "Logic delete a teacher")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "teacher ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3. pagination select teacher list
    @ApiOperation("Pagination select teachers")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,
                             @PathVariable long limit) {
        Page<EduTeacher> pageTeacher =  new Page<>(current,limit);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> teacherList = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",teacherList);
    }


    //4. pagination condition select teacher list
    @ApiOperation("multiple condition select teachers with paginatio")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
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

    //add teacher method
    @ApiOperation(value = "add a new teacher")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean rs = teacherService.save(eduTeacher);
        if(rs){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //base teacher Id to select this teacher's info
    @ApiOperation(value = "")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //modify this teacher info
    @ApiOperation(value = "")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean rs = teacherService.updateById(eduTeacher);
        if(rs){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

