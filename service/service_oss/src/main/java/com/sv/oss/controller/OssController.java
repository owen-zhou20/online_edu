package com.sv.oss.controller;

import com.sv.commonutils.R;
import com.sv.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //upload teacher avatar to Ali Oss
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //get upload file use MultipartFile
        //return url address for this avatar in Ali Oss
        String url = ossService.uploadFileAvatar(file);

        return R.ok().data("url",url);
    }


}
