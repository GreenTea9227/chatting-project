package com.kor.syh.chat.adapter.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.kor.syh.chat.application.port.out.RoomPersistencePort;
import com.kor.syh.chat.application.port.out.RoomsDto;
import com.kor.syh.chat.domain.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RoomPersistenceRepository implements RoomPersistencePort {
	private final SpringMongoRoomRepository springMongoRoomRepository;
	private final RoomMapper roomMapper;

	@Override
	public void saveRoom(Room room) {
		RoomDocument roomDocument = roomMapper.toEntity(room);
		springMongoRoomRepository.save(roomDocument);
	}

	@Override
	public void deleteRoom(String roomId) {
		springMongoRoomRepository.deleteById(roomId);
	}

	@Override
	public void addParticipant(String roomId, String userId) {
		springMongoRoomRepository.addParticipant(roomId, userId);
	}

	@Override
	public void exitRoom(String roomId, String userId) {
		springMongoRoomRepository.removeParticipant(roomId, userId);
	}

	@Override
	public Slice<RoomsDto> findRoomsByPageable(Pageable pageable) {
		Slice<RoomDocument> slicePages = springMongoRoomRepository.findAllBy(pageable);

		List<RoomsDto> mappedRooms = slicePages.getContent().stream()
										   .map(roomMapper::toRoomsDto)
										   .collect(Collectors.toList());

		return new SliceImpl<>(mappedRooms, pageable, slicePages.hasNext());
	}

}
