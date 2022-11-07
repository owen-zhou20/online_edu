package com.sv.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import java.util.List;

public class TestVod {
    public static void main(String[] args) throws Exception {
        String accessKeyId = "KeyId";
        String accessKeySecret = "KeySecret";
        String title = "3 - What If I Want to Move Faster - upload by SDK";
        String fileName = "D:\\BaiduNetdiskDownload\\6 - What If I Want to Move Faster.mp4";

        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

    }

    /**
     * Upload a local file.
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* Specify the size of each part in multipart upload. The default size is 2 MB. */
        request.setPartSize(2 * 1024 * 1024L);
        /* Specify the number of concurrent threads for multipart upload. The default value is 1. Concurrent threads consume CPU resources of the server. You can specify a value based on your server load. */
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); // Specify the ID of the request that is sent to ApsaraVideo VOD.
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* If the callback URL that you specify is invalid, video upload is not affected. The video ID is returned with the error code. If the upload fails in other cases, the video ID is left empty. Analyze the cause based on the returned error code. */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }


    //Query a playback credential
    // 2. Get video credential to play by video id
    public static void getPlayAuth() throws ClientException {

        // create init object
        DefaultAcsClient client = InitObject.initVodClient("KeyId", "KeySecret");

        // create request and response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // set video id into request
        request.setVideoId("d9f9a9f1a0a4420c86ad511c5bfac2d9");

        // Get credential
        response = client.getAcsResponse(request);
        String playauth = response.getPlayAuth();

        System.out.println("playauth: " + playauth);
    }

    // 1. Get video address to play by video id
    public static void getPlayUrl() throws ClientException {

        // Query a playback URL
        // create init object
        DefaultAcsClient client = InitObject.initVodClient("KeyId","KeySecret");

        // create request and response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // set video id into request
        request.setVideoId("d9f9a9f1a0a4420c86ad511c5bfac2d9");

        // Get URL
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        // The playback URL.
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        // The information about the video base.
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
