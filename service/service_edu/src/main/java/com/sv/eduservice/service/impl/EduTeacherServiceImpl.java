package com.sv.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.eduservice.client.OssClient;
import com.sv.eduservice.entity.EduTeacher;
import com.sv.eduservice.mapper.EduTeacherMapper;
import com.sv.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Teacher ServiceImpl
 * </p>
 *
 * @author Owen
 * @since 2022-06-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Autowired
    private OssClient ossClient;

    // pagination select teacher list
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        baseMapper.selectPage(pageTeacher,wrapper);

        // Get pagination select teacher data and put into map
        List<EduTeacher> records = pageTeacher.getRecords();
        long total = pageTeacher.getTotal();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long current = pageTeacher.getCurrent();

        boolean hasNext = pageTeacher.hasNext();
        boolean hasPrevious = pageTeacher.hasPrevious();

        Map<String,Object> map = new HashMap();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //2. Logic delete a teacher
    @Override
    public boolean removeTeacher(String id) {
        EduTeacher eduTeacher = baseMapper.selectById(id);
        if(eduTeacher != null){
            ossClient.deleteOssFile(eduTeacher.getAvatar());
        }
        baseMapper.deleteById(id);
        return true;
    }

    // Get teachers for homepage
    @Cacheable(key = "'selectTeacherList'",value = "homepage")
    @Override
    public List<EduTeacher> getTeacherHomepage() {
        QueryWrapper<EduTeacher> wrapperT = new QueryWrapper<>();
        wrapperT.orderByDesc("sort");
        wrapperT.last("limit 4");
        List<EduTeacher> teacherList = baseMapper.selectList(wrapperT);
        return teacherList;
    }
}
