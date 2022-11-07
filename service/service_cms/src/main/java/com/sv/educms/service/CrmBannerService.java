package com.sv.educms.service;

import com.sv.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // Selete all banners
    List<CrmBanner> selectAllBanner();
}
