package com.challenge.infra.srv.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SrvEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SrvEurekaServerApplication.class, args);
	}

}
