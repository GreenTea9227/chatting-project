package com.kor.syh.application.port.out.channel;

import com.kor.syh.domain.Notify;

public interface MessagePublishPort {

	Long publish(Notify notify);

}
