package com.sv.statistics.controller;


import com.sv.commonutils.R;
import com.sv.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Owen
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/statistics/StaDaily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    // Get count No. of register member for one day
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }

    // Show data, return data json and date json
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }



}

