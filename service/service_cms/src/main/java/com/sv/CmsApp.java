package com.sv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.sv"})
@MapperScan("com.sv.educms.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class CmsApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(CmsApp.class,args);
    }
}
