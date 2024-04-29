package com.kor.syh.loggingservice.adapter.out;

import org.springframework.stereotype.Component;

import com.kor.syh.common.utils.JsonUtil;
import com.kor.syh.loggingservice.application.port.out.SendLogPort;
import com.kor.syh.loggingservice.domain.LogEvent;
import com.kor.syh.loggingservice.domain.LogMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cloudwatch.model.CloudWatchException;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.CreateLogGroupRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.CreateLogStreamRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogGroupsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.DescribeLogStreamsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.ResourceNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CloudWatchLogAdapter implements SendLogPort {
	private final CloudWatchLogsClient cloudWatchLogsClient;

	@Override
	public void sendLog(LogEvent event, long timestamp) {

		String group = event.getGroup();
		String stream = event.getStream();

		try {

			PutLogEventsRequest request = createRequest(event, timestamp, group, stream);
			cloudWatchLogsClient.putLogEvents(request);

		} catch (CloudWatchException e) {
			log.error("Error sending logs to CloudWatch: {}", e.awsErrorDetails().errorMessage());
		} catch (ResourceNotFoundException e) {
			if (!logGroupExists(group)) {
				createLogGroup(group);
			}
			if (!logStreamExists(group, stream)) {
				createLogStream(group, stream);
			}
			log.info("create group: [{}] , stream: [{}]", group, stream);
			throw e;
		}
	}

	private static PutLogEventsRequest createRequest(LogEvent event, long timestamp, String group,
		String stream) {
		LogMessage logMessage = event.getLogMessage();

		InputLogEvent inputLogEvent = InputLogEvent.builder()
												   .message(JsonUtil.classToString(logMessage))
												   .timestamp(timestamp)
												   .build();

		return PutLogEventsRequest.builder()
								  .logGroupName(group)
								  .logStreamName(stream)
								  .logEvents(inputLogEvent)
								  .build();
	}

	private boolean logGroupExists(String logGroupName) {
		DescribeLogGroupsRequest request = DescribeLogGroupsRequest.builder()
																   .logGroupNamePrefix(logGroupName)
																   .limit(1)
																   .build();
		var response = cloudWatchLogsClient.describeLogGroups(request);
		return response.logGroups().stream()
					   .anyMatch(group -> group.logGroupName().equals(logGroupName));
	}

	private boolean logStreamExists(String logGroupName, String logStreamName) {
		DescribeLogStreamsRequest request = DescribeLogStreamsRequest.builder()
																	 .logGroupName(logGroupName)
																	 .logStreamNamePrefix(logStreamName)
																	 .limit(1)
																	 .build();
		var response = cloudWatchLogsClient.describeLogStreams(request);
		return response.logStreams().stream()
					   .anyMatch(stream -> stream.logStreamName().equals(logStreamName));
	}

	private void createLogGroup(String logGroupName) {

		CreateLogGroupRequest logGroupRequest = CreateLogGroupRequest.builder()
																	 .logGroupName(logGroupName)
																	 .build();
		cloudWatchLogsClient.createLogGroup(logGroupRequest);

	}

	private void createLogStream(String logGroupName, String logStreamName) {
		CreateLogStreamRequest logStreamRequest = CreateLogStreamRequest.builder()
																		.logGroupName(logGroupName)
																		.logStreamName(logStreamName)
																		.build();
		cloudWatchLogsClient.createLogStream(logStreamRequest);
	}
}
