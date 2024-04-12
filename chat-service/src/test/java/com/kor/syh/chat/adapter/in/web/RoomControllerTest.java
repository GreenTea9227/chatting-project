package com.kor.syh.chat.adapter.in.web;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.kor.syh.chat.adapter.out.persistence.RoomDocument;
import com.kor.syh.common.IntegrationTest;
import com.kor.syh.testsupport.IntegrationTestEnvironment;

@IntegrationTest
class RoomControllerTest extends IntegrationTestEnvironment {

	@DisplayName("유저가 방을 생성한다.")
	@Test
	void createRoom() throws Exception {
		//when
		ResultActions perform = mvc.perform(post("/room/create")
			.header("X-Authorization-Id", "userId"));

		//then
		perform
			.andExpect(jsonPath("$.data.roomId").isNotEmpty())
			.andExpect(jsonPath("$.status").value("success"))
			.andExpect(jsonPath("$.message").value("success"));

		MvcResult mvcResult = perform.andReturn();
		String responseContent = mvcResult.getResponse().getContentAsString();
		JSONObject jsonResponse = new JSONObject(responseContent);
		String roomId = jsonResponse.getJSONObject("data").getString("roomId");

		List<RoomDocument> documents =
			mongoTemplate.find(Query.query(Criteria.where("_id").is(roomId)), RoomDocument.class);

		assertThat(documents.size()).isEqualTo(1);
		assertThat(documents.get(0).getId()).isEqualTo(roomId);
		assertThat(documents.get(0).getUpdateDate()).isBeforeOrEqualTo(LocalDateTime.now());
	}

	@DisplayName("유저가 방 목록을 가져온다.")
	@Test
	void getRooms() throws Exception {
		// given
		for (int i = 0; i < 12; i++) {
			RoomDocument room = RoomDocument.builder()
											.roomId("room" + i)
											.build();

			mongoTemplate.save(room, "room");
		}
		// when
		ResultActions perform = mvc.perform(get("/room/rooms")
			.param("page", "1")
			.param("size", "10"));

		// then
		perform.andExpect(status().isOk())
			   .andExpect(jsonPath("$.status").value("success"))
			   .andExpect(jsonPath("$.message").value("success"))
			   .andExpect(jsonPath("$.data.content", hasSize(2)))
			   .andExpect(jsonPath("$.data.content[0].roomId", is(notNullValue())));
	}

}