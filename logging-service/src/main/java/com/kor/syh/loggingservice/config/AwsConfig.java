package com.kor.syh.loggingservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;

@Slf4j
@Profile("aws")
@Configuration
public class AwsConfig {

	@Value("${aws.access_key}")
	private String accessKey;
	@Value("${aws.secret_key}")
	private String secretKey;


	@Bean
	public CloudWatchLogsClient hello() {
		AwsBasicCredentials credentials = AwsBasicCredentials.create(
			accessKey, secretKey
		);

		CloudWatchLogsClient cloudWatchLogsClient = CloudWatchLogsClient.builder()
																		.credentialsProvider(
																			StaticCredentialsProvider.create(
																				credentials))
																		.region(Region.AP_NORTHEAST_2)
																		.build();

		return cloudWatchLogsClient;
	}

}
