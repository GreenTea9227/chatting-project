package com.kor.syh.chat.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kor.syh.chat.adapter.out.web.RoomResponseDto;
import com.kor.syh.chat.application.port.in.HandlerRoomUseCase;
import com.kor.syh.common.CommonResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {

	private final HandlerRoomUseCase handlerRoomUseCase;

	@PostMapping("/create")
	public CommonResponse<?> createRoom() {

		//TODO change userID
		RoomResponseDto roomResponseDto = handlerRoomUseCase.createRoom("userId");
		return CommonResponse.success(roomResponseDto, "success");
	}
}
