package com.kor.syh.chat.adapter.in.web;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageDto {
	@Min(0)
	private int page;
	@Min(0)
	private int size;
}
