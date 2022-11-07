package com.sv.vod;


import com.sv.vod.utils.ConstantVodUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.sv"})
@EnableDiscoveryClient
public class VodApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(VodApp.class,args);
    }
}
