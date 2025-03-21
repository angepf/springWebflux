package com.challenge.api.customer.management;

import com.challenge.api.customer.management.configuration.AwsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AwsProperties.class})
public class MsaCustomerManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaCustomerManagementApplication.class, args);
	}

}
