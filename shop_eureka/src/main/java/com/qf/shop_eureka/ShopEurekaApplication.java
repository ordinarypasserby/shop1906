package com.qf.shop_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaServer
public class ShopEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopEurekaApplication.class, args);
    }

}
