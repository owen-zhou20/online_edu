package com.sv.eduservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * Comment service
 * </p>
 *
 * @author Owen
 * @since 2023-07-27
 */
public interface EduCommentService extends IService<EduComment> {

    // Add a comment by member Id
    boolean saveComment(EduComment comment, String memberId);

    // Pagination select page by course Id
    Map<String, Object> getListCommentById(Page<EduComment> pageParam, QueryWrapper<EduComment> wrapper);
}
