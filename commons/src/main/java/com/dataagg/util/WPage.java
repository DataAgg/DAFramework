package com.dataagg.util;

public class WPage {
	private int from = 0; //开始索引
	private int size = 20; //每页数量
	private Integer total = 0; //总数
 
	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public void nextItem() {
		from++;
	}

	public void preItem() {
		from--;
	}
}
