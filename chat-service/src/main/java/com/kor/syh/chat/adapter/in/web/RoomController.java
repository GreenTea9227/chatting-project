package com.kor.syh.chat.adapter.in.web;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kor.syh.chat.application.port.in.HandlerRoomUseCase;
import com.kor.syh.chat.application.port.out.RoomsDto;
import com.kor.syh.chat.domain.Room;
import com.kor.syh.common.CommonResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomController {

	private final HandlerRoomUseCase handlerRoomUseCase;

	@PostMapping("/create")
	public CommonResponse<?> createRoom(@RequestHeader("X-Authorization-Id") String userId) {

		RoomResponseDto roomResponseDto = handlerRoomUseCase.createRoom(userId);
		return CommonResponse.success(roomResponseDto, "success");
	}

	@GetMapping("/rooms")
	public CommonResponse<Slice<RoomsDto>> getRoomList(@Valid PageDto pageDto) {

		Slice<RoomsDto> rooms = handlerRoomUseCase.getRooms(pageDto);

		return CommonResponse.success(rooms, "success");

	}

	@GetMapping("/rooms/{roomId}")
	public CommonResponse<?> enterRoom(
		@PathVariable("roomId") String roomId,
		@RequestHeader("X-Authorization-Id") String userId) {
		handlerRoomUseCase.enterRoom(roomId, userId);
		return CommonResponse.success("success");
	}

}
