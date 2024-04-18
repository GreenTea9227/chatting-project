package com.kor.syh.loggingservice.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * LogMessage 클래스는 로깅 데이터를 구조화하기 위해 사용됩니다.
 * 이 클래스는 로그 그룹, 로그 스트림, 그리고 로그 메시지를 포함합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LogEvent {
	/**
	 * group - 애플리케이션 이름을 나타냅니다. 이는 로그를 그룹화하는 데 사용됩니다.
	 */
	private String group;

	/**
	 * stream - 애플리케이션을 구분하는 유니크한 아이디입니다.
	 * 이는 동일 애플리케이션 내에서 발생하는 로그 스트림을 식별하는 데 사용됩니다.
	 */
	private String stream;

	/**
	 * message - 로그의 실제 내용을 나타냅니다.
	 */
	private LogMessage logMessage;
}
