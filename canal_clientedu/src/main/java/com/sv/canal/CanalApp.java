package com.sv.canal;

import com.sv.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class CanalApp implements CommandLineRunner {

    @Resource
    private CanalClient canalClient;

    public static void main( String[] args )
    {
        SpringApplication.run(CanalApp.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        //项目启动，执行canal客户端监听
        canalClient.run();
    }
}
