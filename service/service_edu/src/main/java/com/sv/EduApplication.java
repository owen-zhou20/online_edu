package com.sv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.sv"})
public class EduApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(EduApplication.class, args);
    }
}
