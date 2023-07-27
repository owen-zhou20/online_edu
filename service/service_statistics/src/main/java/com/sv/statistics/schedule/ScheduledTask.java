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
     * test scheduled task
     * Run once every 5s from 7:00 AM to 23:00 PM per day

    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("*********++++++++++++*****task1 running!");
    }
     */

}
