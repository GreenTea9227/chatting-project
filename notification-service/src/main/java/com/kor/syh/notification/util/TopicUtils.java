package com.kor.syh.notification.util;

import static com.kor.syh.common.constant.RedisKey.*;

public class TopicUtils {
	public static String createTopic(String key) {
		return TOPIC_PREFIX + key;
	}

	public static String extractTopic(String fullName) {
		return fullName.substring(TOPIC_PREFIX.length());
	}
}
