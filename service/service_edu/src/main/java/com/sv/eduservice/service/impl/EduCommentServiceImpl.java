package com.sv.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.ordervo.UcenterMemberComment;
import com.sv.eduservice.client.UcenterClient;
import com.sv.eduservice.entity.EduComment;
import com.sv.eduservice.mapper.EduCommentMapper;
import com.sv.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Comment serviceImpl
 * </p>
 *
 * @author Owen
 * @since 2023-07-27
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    @Autowired
    private UcenterClient ucenterClient;


    // Pagination select page by course Id
    @Override
    public Map<String, Object> getListCommentById(Page<EduComment> pageParam, QueryWrapper<EduComment> wrapper) {
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam, wrapper);

        List<EduComment> commentList  = pageParam.getRecords();

        Map<String,Object> map = new HashMap<>();
        map.put("items",commentList);
        map.put("total",pageParam.getTotal());
        map.put("size",pageParam.getSize());
        map.put("current",pageParam.getCurrent());
        map.put("pages",pageParam.getPages());

        map.put("hasNext",pageParam.hasNext());
        map.put("hasPrevious",pageParam.hasPrevious());

        return map;
    }


    // Add a comment by member Id
    @Override
    public boolean saveComment(EduComment comment, String memberId) {
        comment.setMemberId(memberId);

        UcenterMemberComment memberInfo = ucenterClient.getMemberInfoComment(memberId);

        if(memberInfo==null){
            throw new SvException(20001,"Fail to get member info from Ucenter member!");
        }
        comment.setNickname(memberInfo.getNickname());
        comment.setAvatar(memberInfo.getAvatar());

        int rs = baseMapper.insert(comment);

        return rs>0;
    }
}
