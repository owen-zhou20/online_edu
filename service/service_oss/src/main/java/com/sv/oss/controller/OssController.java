package com.sv.oss.controller;

import com.sv.commonutils.R;
import com.sv.oss.service.OssService;
import io.swagger.annotations.Api;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * upload a file to Ali Oss
 * upload teacher avatar to Ali Oss
 */
@RestController
@Api(tags = "Upload a file/teacher avatar to ALI OSS")
@RequestMapping("/eduoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //upload teacher avatar to Ali Oss
    @PostMapping("uploadOssFile")
    public R uploadOssFile(MultipartFile file){
        //get upload file use MultipartFile
        //return url address for this avatar in Ali Oss
        String url = ossService.uploadFile(file);
        if(Strings.isNotEmpty(url)){
            return R.ok().data("url",url);
        }else{
            return R.error();
        }

    }


}
