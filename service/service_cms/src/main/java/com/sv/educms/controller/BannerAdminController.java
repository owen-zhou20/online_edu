package com.sv.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.commonutils.R;
import com.sv.educms.entity.CrmBanner;
import com.sv.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  Admin banner API
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
@RestController
@RequestMapping("/educms/banneradmin")
//@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    // Get a banner info by id
    @GetMapping("get/{id}")
    public R get(@PathVariable String id){
        CrmBanner banner = bannerService.getBannerById(id);
        return R.ok().data("banner", banner);
    }

    // Pagination select banner list
    @PostMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit,
                        @RequestBody(required = false) CrmBanner crmBanner) {
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.pageBanner(pageBanner,crmBanner);
        return R.ok().data("items", pageBanner.getRecords()).data("total", pageBanner.getTotal());
    }

    // Add a banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.saveBanner(crmBanner);
        return R.ok();
    }

    // Update a banner info by id
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner crmBanner){
        bannerService.updateBannerById(crmBanner);
        return R.ok();
    }

    // Delete a banner by id
    @DeleteMapping("remove/{id}")
    public R removeBanner(@PathVariable String id){
        bannerService.removeBannerById(id);
        return R.ok();
    }
}

