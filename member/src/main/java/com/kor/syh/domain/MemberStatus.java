package com.kor.syh.domain;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MemberStatus {
	USER, DELETE, BAN;

	private static final Map<String, MemberStatus> map = Collections.unmodifiableMap(Stream.of(values()).collect(
		Collectors.toMap(MemberStatus::name, Function.identity())));

	public static MemberStatus find(String value) {
		return map.get(value.toUpperCase());
	}
}
