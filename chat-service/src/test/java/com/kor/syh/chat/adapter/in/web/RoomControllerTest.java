package com.kor.syh.chat.adapter.in.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


import com.kor.syh.testsupport.IntegrationTestEnvironment;
import com.kor.syh.common.IntegrationTest;

@IntegrationTest
class RoomControllerTest extends IntegrationTestEnvironment {

	@Autowired
	private MockMvc mvc;

	@Test
	void createRoom() {

	}
}