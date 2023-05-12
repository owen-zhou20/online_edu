package com.sv;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.sv")
@MapperScan("com.sv.aclservice.mapper")
public class AclApp {

    public static void main(String[] args) {
        SpringApplication.run(AclApp.class, args);
    }

}
