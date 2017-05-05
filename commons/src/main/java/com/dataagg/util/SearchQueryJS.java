package com.dataagg.util;

import java.io.Serializable;

import org.nutz.dao.pager.Pager;

public class SearchQueryJS implements Serializable{
	private static final long serialVersionUID = -559520775581829448L;
	private WPage page;//分页信息
	private WMap query;//查询条件

	public WPage getPage() {
		return page;
	}

	public void setPage(WPage page) {
		this.page = page;
	}

	public WMap getQuery() {
		return query;
	}

	public void setQuery(WMap query) {
		this.query = query;
	}

	public Pager toPager(){
		if(page != null){
			return new Pager(page.getFrom(), page.getSize());
		}
		return new Pager(Constans.Page_No, Constans.Page_Size);
	}
	public Pager toPager(int pageSize){
		if(page != null){
			return new Pager(page.getFrom(), pageSize);
		}
		return new Pager(Constans.Page_No, pageSize);
	}
	public WPage toWPage(Pager pager){
		WPage page = new WPage();
		if(pager != null){
			page.setFrom(pager.getPageNumber());
			page.setSize(pager.getPageSize());
			page.setTotal(pager.getRecordCount());
		}
		return page;
	}
}
