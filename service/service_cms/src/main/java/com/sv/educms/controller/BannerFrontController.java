package com.sv.educms.controller;


import com.sv.commonutils.R;
import com.sv.educms.entity.CrmBanner;
import com.sv.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  Front-end to show banners
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    // Selete all banners for homepage
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> bannerList = bannerService.selectIndexList();
        return R.ok().data("bannerList",bannerList);
    }

}

