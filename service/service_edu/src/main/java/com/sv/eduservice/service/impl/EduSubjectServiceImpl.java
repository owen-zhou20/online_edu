package com.sv.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.eduservice.entity.EduSubject;
import com.sv.eduservice.entity.excel.SubjectData;
import com.sv.eduservice.entity.subject.OneSubject;
import com.sv.eduservice.entity.subject.TwoSubject;
import com.sv.eduservice.listener.SubjectExcelListener;
import com.sv.eduservice.mapper.EduSubjectMapper;
import com.sv.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-07-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //add course subject
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try{
            //file input steam
            InputStream in = file.getInputStream();
            //use EasyExcel to read file from input steam
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();

        }catch(Exception e){
            e.printStackTrace();
            throw new SvException(20001,"file to upload subject");
        }

    }

    // course subject list tree
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // Get all one subjects, parent_id = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // Get all two subjects, parent_id != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // final list
        List<OneSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            // Get each EduSubject form oneSubjectList
            EduSubject eduSubject = oneSubjectList.get(i);
            // Get values from eduSubject and out them into OneSubject
            OneSubject oneSubject = new OneSubject();
            /*oneSubject.setId(eduSubject.getId());
            oneSubject.setTitle(eduSubject.getTitle());*/
            BeanUtils.copyProperties(eduSubject, oneSubject);


            // put level two subjects in the children in each level one subject
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //Put two subjects into twoFinalSubjectList for each one subject
            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject twoEduSubject = twoSubjectList.get(m);
                // Put two subjects into twoFinalSubjectList for each one subject
                if(twoEduSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // Put two subjects into each one subject
            oneSubject.setChildren(twoFinalSubjectList);
            // Put oneSubject into FinalSubjectList
            finalSubjectList.add(oneSubject);
        }


        return finalSubjectList;
    }
}
