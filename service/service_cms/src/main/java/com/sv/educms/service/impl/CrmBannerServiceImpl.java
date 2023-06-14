package com.sv.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.educms.entity.CrmBanner;
import com.sv.educms.mapper.CrmBannerMapper;
import com.sv.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    // Selete all banners
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // select top 2 banners info order by id
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");

        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);

        return bannerList;
    }
}
