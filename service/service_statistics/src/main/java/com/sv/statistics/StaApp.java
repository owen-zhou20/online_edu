package com.sv.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;


@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.sv"})
@MapperScan("com.sv.statistics.mapper")
@SpringBootApplication
@EnableScheduling
public class StaApp {
    public static void main(String[] args) {
        SpringApplication.run(StaApp.class, args);
    }
}
