package com.kor.syh.loggingservice.application.port.out;

import com.kor.syh.loggingservice.domain.LogEvent;

public interface SendLogPort {
	void sendLog(LogEvent event, long timestamp);
}
