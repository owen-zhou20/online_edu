package com.sv.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    //upload teacher avatar to Ali OSS
    String uploadFile(MultipartFile file);


    // delete object from Ali OSS
    void deleteOssFile(String avatar);
}
