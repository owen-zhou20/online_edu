package com.sv.vod.utils;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;


public class InitVodClient {

    // Specify the AccessKey pair.
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // Specify the region from which you want to access ApsaraVideo VOD.
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public static DefaultAcsClient initVodClientSingapore(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "Singapore";  // Specify the region from which you want to access ApsaraVideo VOD.
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
