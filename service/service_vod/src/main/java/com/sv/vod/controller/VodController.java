package com.sv.vod.controller;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.sv.commonutils.R;
import com.sv.servicebase.exceptionhandler.SvException;
import com.sv.vod.service.VodService;
import com.sv.vod.utils.ConstantVodUtils;
import com.sv.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    // 1. Upload a video to Ali Vod
    @PostMapping("uploadVideoAliVod")
    public R uploadVideoAliVod(MultipartFile file){
        // return upload video id
        String videoId = vodService.uploadVideoAliVod(file);
        return R.ok().data("videoId",videoId);
    }

    // 2. Delete a upload video from Ali VOD
    @DeleteMapping("removeAliVodVideo/{id}")
    public R removeAliVodVideo(@PathVariable String id){
        try{
            // Init client
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // Init request and response for delete
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            request.setVideoIds(id);
            response = client.getAcsResponse(request);
            return R.ok();

        }catch(Exception e){
            e.printStackTrace();
            throw new SvException(20001, "Fail to delete this video!");
        }
    }

    // 3. Batch delete videos
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAliVodVideo(videoIdList);
        return R.ok();
    }

    // 4. Get video play auth by video id
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try{
            // init entity
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // Get play auth
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            System.out.println("--------"+id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            System.out.println("=======" +playAuth);
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e){
            throw new SvException(20001,"Fail to get playAuth");
        }
    }


}
