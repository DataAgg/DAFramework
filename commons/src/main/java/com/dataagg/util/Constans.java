package com.dataagg.util;

import jodd.util.StringUtil;

public class Constans {
	/**
	 * 分页索引
	 * */
	public static final int Page_No  = 1;
	/**
	 * 分页-每页数目
	 * */
	public static final int Page_Size  = 20;
	
	/**
	 * 正反
	 * 正 1 反 0
	 * 像删除标记，都可以使用
	 */
	public static class POS_NEG{
		
		/**
		 * 1 正 是 
		 */
		public static final String POS  = "1";
		
		/**
		 * 0 反 否  
		 */
		public static final String NEG = "0";
	}
	
	/**
	 * 性别
	 */
	public static class Sex {
		/**
		 * 1 男
		 */
		public static final String Man  = "1";
		/**
		 * 2 女
		 */
		public static final String Woman  = "2";
		
		public static String getLable(String type) {
			if(StringUtil.isBlank(type)){
				return "";
			}
			switch (type) {
			case Man: 
				return "男";
			case Woman: 
				return "女";
			default:
				return "";
			}
		}
	}
 


}
