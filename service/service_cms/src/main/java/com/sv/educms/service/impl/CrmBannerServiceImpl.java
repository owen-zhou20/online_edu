package com.sv.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sv.educms.client.OssClient;
import com.sv.educms.entity.CrmBanner;
import com.sv.educms.mapper.CrmBannerMapper;
import com.sv.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * Homepage banner serviceImpl
 * </p>
 *
 * @author Owen
 * @since 2022-09-13
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private OssClient ossClient;

    // Selete all banners
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {
        // select top 2 banners info order by id
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);

        return bannerList;
    }

    // Pagination select banner list
    @Override
    public void pageBanner(Page<CrmBanner> pageParam, CrmBanner crmBanner) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(crmBanner.getTitle())){
            wrapper.like("title",crmBanner.getTitle());
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(pageParam,wrapper);
    }


    // Get a banner info by id
    @Override
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    // Add a banner
    @CacheEvict(value = "banner", allEntries=true) // clear all banner redis cache after return
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    // Update a banner info by id
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    // Delete a banner by id
    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        CrmBanner crmBanner = baseMapper.selectById(id);
        if(crmBanner != null){
            ossClient.deleteOssFile(crmBanner.getImageUrl());
        }

        baseMapper.deleteById(id);
    }
}
