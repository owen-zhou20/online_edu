package com.sv.statistics.schedule;

import com.sv.statistics.service.StatisticsDailyService;
import com.sv.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 每天凌晨1点执行定时
     * Run once every day in AM 1:00
     * Select statistics data for yesterday and add to database
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        // Get yesterday
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        staService.registerCount(day);

    }

    /**
     * 测试 test scheduled task
     * 每天七点到二十三点每五秒执行一次

    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("*********++++++++++++*****执行了");
    }
     */

}
