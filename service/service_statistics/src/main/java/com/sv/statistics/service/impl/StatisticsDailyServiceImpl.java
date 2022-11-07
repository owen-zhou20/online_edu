package com.sv.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sv.commonutils.R;
import com.sv.servicebase.exceptionhandler.SvException;
import com.sv.statistics.client.UcenterClient;
import com.sv.statistics.entity.StatisticsDaily;
import com.sv.statistics.mapper.StatisticsDailyMapper;
import com.sv.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Owen
 * @since 2022-10-26
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    // Get count No. of register member for one day
    @Override
    public void registerCount(String day) {
        // delete all data for this day before add
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);


        // Get register count from service_ucenter use openfeign
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        // put data into statistics_daily table in online_edu database
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); // register No.
        sta.setDateCalculated(day); // which day

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));

        baseMapper.insert(sta);

        return;

    }

    // Show data, return data json and date json
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        // Get data by conditions
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        System.out.println("staList ===>" + staList);

        // Put staList to date_calculatedList and numDataList
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        for (StatisticsDaily sta : staList) {
            date_calculatedList.add(sta.getDateCalculated());
            switch (type){
                case "login_num":
                    numDataList.add(sta.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(sta.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(sta.getCourseNum());
                    break;
                default:
                    new SvException(20001,"Error to get type for statistics daily!");
                    break;
            }
        }
        // Put date_calculatedList and numDataList to map and return
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);

        return map;
    }
}
