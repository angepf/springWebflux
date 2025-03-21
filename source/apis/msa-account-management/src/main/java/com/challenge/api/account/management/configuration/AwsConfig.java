package com.challenge.api.account.management.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AwsConfig {

    AwsProperties awsProperties;

    @Bean
    public AwsCredentialsProvider sqsCredentialsProvider() {
        return StaticCredentialsProvider
                .create(AwsBasicCredentials
                        .create(awsProperties.getSqs().getAccessKey(),
                                awsProperties.getSqs().getSecretKey()));
    }

    @Bean
    public SqsClient sqsClient(AwsCredentialsProvider sqsCredentialsProvider) {
        return SqsClient.builder()
                .region(Region.of(awsProperties.getSqs().getRegion()))
                .credentialsProvider(sqsCredentialsProvider).build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient(AwsCredentialsProvider sqsCredentialsProvider) {
        return SqsAsyncClient.builder()
                .region(Region.of(awsProperties.getSqs().getRegion()))
                .credentialsProvider(sqsCredentialsProvider).build();
    }
}