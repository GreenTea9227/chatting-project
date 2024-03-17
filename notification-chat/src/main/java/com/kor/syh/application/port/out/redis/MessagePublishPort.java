package com.kor.syh.application.port.out.redis;

import com.kor.syh.domain.Notify;

public interface MessagePublishPort {
	void subscribe(String channelName);

	Long publish(Notify notify);

	void removeSubscribe(String channelName);
}
