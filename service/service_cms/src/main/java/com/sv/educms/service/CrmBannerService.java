package com.sv.educms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * Homepage banner service
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // Selete all banners
    List<CrmBanner> selectIndexList();

    // Pagination select banner list
    void pageBanner(Page<CrmBanner> pageBanner, CrmBanner crmBanner);

    // Add a banner
    void saveBanner(CrmBanner crmBanner);

    // Update a banner info by id
    void updateBannerById(CrmBanner crmBanner);

    // Delete a banner by id
    void removeBannerById(String id);

    // Get a banner info by id
    CrmBanner getBannerById(String id);
}
