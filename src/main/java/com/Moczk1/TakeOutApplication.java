package com.Moczk1;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.servlet.annotation.ServletSecurity;

@Slf4j
@SpringBootApplication
@ServletComponentScan
//@ServletSecurity
public class TakeOutApplication {
    public static void main(String[] args) {
        SpringApplication.run(TakeOutApplication.class,args);
        log.info("Application starts runnning!")    ;
    }
}
