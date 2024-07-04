package org.mcs.productv1service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Productv1ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Productv1ServiceApplication.class, args);
    }

}
