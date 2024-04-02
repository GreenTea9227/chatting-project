package com.kor.syh.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisKey {
	public static final String TOPIC_PREFIX = "topic:";
	public static final String ROOM_PREFIX = "room:";
	public static final String LOGIN_PREFIX = "login:";
}
