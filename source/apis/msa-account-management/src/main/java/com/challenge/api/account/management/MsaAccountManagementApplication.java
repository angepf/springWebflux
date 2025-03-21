package com.challenge.api.account.management;

import com.challenge.api.account.management.configuration.AwsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AwsProperties.class})
public class MsaAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaAccountManagementApplication.class, args);
	}

}
