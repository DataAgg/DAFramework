package com.dataagg.commons.vo;

/**
 * Created by watano on 2016/12/22.
 */
public class ActionResultObj implements IActionResult<Object> {
	private int code;
	private String message;
	private Object data;

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}
}
