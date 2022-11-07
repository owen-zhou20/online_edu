package com.sv.vodtest;

import com.aliyun.oss.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;


public class InitObject {

    // Specify the AccessKey pair.
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // Specify the region from which you want to access ApsaraVideo VOD.
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
