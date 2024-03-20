package com.kor.syh.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CommonResponse<T> {
	private static final String SUCCESS_STATUS = "success";
	private static final String FAIL_STATUS = "fail";
	private static final String ERROR_STATUS = "error";

	private final String status;
	private final T data;
	private final String message;

	public static <T> CommonResponse<T> success(T data,String message) {
		return CommonResponse.of(SUCCESS_STATUS, data, null);
	}

	public static CommonResponse<?> success(String message) {
		return CommonResponse.of(SUCCESS_STATUS, null, message);
	}


	public static <T> CommonResponse<T> fail(T data,String message) {
		return CommonResponse.of(FAIL_STATUS, data, message);
	}

	public static  CommonResponse<?> fail(String message) {
		return CommonResponse.of(FAIL_STATUS, null, message);
	}
	public static <T> CommonResponse<T> error(T data,String message) {
		return CommonResponse.of(ERROR_STATUS, data, message);
	}

}
