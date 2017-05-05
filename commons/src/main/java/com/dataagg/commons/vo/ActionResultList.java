package com.dataagg.commons.vo;

import java.util.List;

/**
 * Created by watano on 2016/12/22.
 */
public class ActionResultList<T> implements IActionResult<List<T>> {
	private int code;
	private String message;
	private List<T> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
