package com.sv.eduservice.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.JwtUtils;
import com.sv.commonutils.R;
import com.sv.eduservice.entity.EduComment;
import com.sv.eduservice.service.EduCommentService;
import com.sv.servicebase.exceptionhandler.SvException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * Front comment controller
 * </p>
 *
 * @author Owen
 * @since 2023-07-27
 */
@RestController
@RequestMapping("/eduservice/commentfront")
public class CommentFrontController {

    @Autowired
    private EduCommentService commentService;

    // Pagination select page by course Id
    @ApiOperation(value = "Pagination select page by course Id")
    @GetMapping("{page}/{limit}")
    public R getListCommentById(@ApiParam(name = "page", value = "current page", required = true)
                            @PathVariable long page,
                            @ApiParam(name = "limit", value = "limit records for a page", required = true)
                            @PathVariable long limit,
                            @ApiParam(name = "courseQuery",value = "Course id for query", required = true)
                            String courseId) {
        Page<EduComment> pageParam = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        Map<String,Object> map = commentService.getListCommentById(pageParam, wrapper);

        return R.ok().data(map);
    }

    // Add a comment by member Id
    @ApiOperation(value = "Add a comment by member Id")
    @PostMapping("save")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // check member Id
        if(StringUtils.isEmpty(memberId)){
            throw new SvException(20001,"Please login!");
        }

        boolean flag = commentService.saveComment(comment,memberId);

        if(flag){
            return R.ok();
        } else {
            return R.error().message("Fail to save this comment!");
        }


    }

}

