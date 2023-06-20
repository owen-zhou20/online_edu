package com.sv.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.sv.commonutils.R;
import com.sv.servicebase.exceptionhandler.SvException;
import com.sv.vod.service.VodService;
import com.sv.vod.utils.ConstantVodUtils;
import com.sv.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    // 1. Upload video to Ali Vod
    @Override
    public String uploadVideoAliVod(MultipartFile file) {
        try{
            // filename: upload file original name
            String fileName = file.getOriginalFilename();
            // title: file name for upload file in VOD
            String title = fileName.substring(0,fileName.lastIndexOf("."));
            // inputStream: upload file input stream
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);
            /* Optional. Specify whether to apply the default watermark. You can specify whether to apply the default watermark based on the template group configuration. */
            //request.setShowWaterMark(true);
            /* Customize the callback configuration for event notifications. */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://demo.example.com\"}}"");
            /* Optional. Specify the category ID of the video. */
            //request.setCateId(0);
            /* Optional. Specify the tags of the video. Separate multiple tags with commas (,). */
            // request.setTags("tag 1,tag 2");
            /* Optional. Specify the description of the video. */
            // request.setDescription("Video description");
            /* Optional. Specify the thumbnail of the video. */
            //request.setCoverURL("http://cover.example.com/image_01.jpg");
            /* Optional. Specify the ID of the template group. */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56****");
            /* Optional. Specify the ID of the workflow. */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
            /* Optional. Specify the storage location. */
            //request.setStorageLocation("in-201703232118266-5sejd****.oss-cn-shanghai.aliyuncs.com");
            /* Enable the default callback for the upload progress. */
            // request.setPrintProgress(true);
            /* Specify the custom callback for the upload progress. The callback must inherit VoDProgressListener. */
            // request.setProgressListener(new PutObjectProgressListener());
            /* Specify the ID of the application. */
            //request.setAppId("app-100****");
            /* Specify the access region of ApsaraVideo VOD. */
            //request.setApiRegionId("cn-shanghai");
            /* Specify the region in which the ECS instance is deployed. */
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //System.out.print("RequestId=" + response.getRequestId() + "\n"); // Specify the ID of the request that is sent to ApsaraVideo VOD.
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { // If the callback URL that you specify is invalid, video upload is not affected. The video ID is returned with the error code. If the upload failure is not caused by an invalid callback URL, the ID of the video is empty. Identify the cause of the error based on the returned error code.
                //System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                if(StringUtils.isEmpty(videoId)){
                    throw new SvException(20001, response.getMessage());
                }
                videoId = response.getVideoId();
            }
            return videoId;
        } catch(Exception e){
            e.printStackTrace();
            throw new SvException(20001,"File to upload this video");
        }
    }

    // 2. Delete a video from Ali VOD
    @Override
    public void removeAliVodVideo(String id) {
        try{
            // Init client
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // Init request and response for delete
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            request.setVideoIds(id);
            response = client.getAcsResponse(request);
        }catch(Exception e){
            e.printStackTrace();
            throw new SvException(20001, "Fail to delete this video!");
        }
    }

    // 3. Batch delete videos
    @Override
    public void removeMoreAliVodVideo(List videoIdList) {
        try{
            // Init client
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // Init request and response for delete
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            response = client.getAcsResponse(request);
        }catch(Exception e){
            e.printStackTrace();
            throw new SvException(20001, "Fail to delete videos!");
        }
    }


}
