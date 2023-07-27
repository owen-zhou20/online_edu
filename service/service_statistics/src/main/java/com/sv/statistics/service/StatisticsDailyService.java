package com.sv.statistics.service;

import com.sv.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * Statistics daily service
 * </p>
 *
 * @author Owen
 * @since 2022-10-26
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    // Get count No. of register member for one day
    void registerCount(String day);

    // Show data, return data json and date json
    Map<String, Object> getShowData(String type, String begin, String end);
}
