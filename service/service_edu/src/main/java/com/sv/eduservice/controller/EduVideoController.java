package com.sv.eduservice.controller;


import com.sv.commonutils.R;
import com.sv.eduservice.client.VodClient;
import com.sv.eduservice.entity.EduChapter;
import com.sv.eduservice.entity.EduVideo;
import com.sv.eduservice.service.EduVideoService;
import com.sv.servicebase.exceptionhandler.SvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * course video controller
 * </p>
 *
 * @author Owen
 * @since 2022-08-11
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    // Add video
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean rs = videoService.save(eduVideo);
        if(!rs){
            throw new SvException(20001,"Fail to add this video");
        }
        return R.ok();
    }

    // Delete video also delete video from Ali VOD  TODO serviceImpl
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        // Get Vod video source id by video id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        // Delete Vod video if it has
        if(!StringUtils.isEmpty(videoSourceId)){
            R rs = vodClient.removeAliVodVideo(videoSourceId);
            //System.out.println(rs.getCode());
            if(rs.getCode() == 20001){
                throw new SvException(20001, "Fail to delete this video from Ali VOD!");
            }
        }
        // Delete video
        videoService.removeById(id);
        return R.ok();

    }

    // Modify video TODO
    @PutMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean rs = videoService.updateById(eduVideo);
        if(!rs){
            throw new SvException(20001,"Fail to modify this video");
        }
        return R.ok();
    }

    // Get video info by video id
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        if(eduVideo == null){
            throw new SvException(20001,"This video is not exist!");
        }
        return R.ok().data("video",eduVideo);
    }

}

