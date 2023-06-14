package com.sv.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    //upload teacher avatar to Ali Oss
    String uploadFile(MultipartFile file);


}
