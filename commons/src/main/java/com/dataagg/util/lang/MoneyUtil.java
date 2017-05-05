package com.dataagg.util.lang;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import jodd.util.StringUtil;

public class MoneyUtil {
	/**
	 * 判断2个金额是否相等。因为存储精度的问题，当2个金额差额小于0.001时，就认为这2个金额相等
	 *
	 * @param money1
	 * @param money2
	 * @return
	 */
	public static boolean equals(Double money1, Double money2) {
		return (money1 == null && money2 == null)
				|| (money1 != null && money2 != null && Math.abs(money1 - money2) < 0.001);
	}

	/**
	 * 修正金额，精确到2位小数
	 *
	 * @param price
	 * @return
	 */
	public static Double fixPrice(Double price) {
		return ArithUtils.round(price, 2);
	}

	/**
	 * 修正折扣(以10为单位)，精确到1位小数
	 *
	 * @param discount
	 * @return
	 */
	public static Double fixDiscount(Double discount) {
		return ArithUtils.round(discount, 1);
	}

	/**
	 * 获取一组金额(单价*数量)加权平均的结果
	 *
	 * @param amounts
	 * @return
	 */
	public static Double getWeightedAverage(Map<Double, Long> amounts) {
		Double totalAmount = 0d;
		Long quantity = 0L;
		if (amounts != null && amounts.size() > 0) {
			for (Double money : amounts.keySet()) {
				Double amount = ArithUtils.mul(money, amounts.get(money).doubleValue());
				totalAmount = ArithUtils.add(totalAmount, amount);
				quantity += amounts.get(money);
			}
		}
		return ArithUtils.div(totalAmount, quantity.doubleValue());
	}

	/**
	 * 获取2个金额(单价*数量)加权平均的结果
	 *
	 * @param money1
	 * @param quantity1
	 * @param money2
	 * @param quantity2
	 * @return
	 */
	public static Double getWeightedAverage(Double money1, Long quantity1, Double money2, Long quantity2) {
		if (equals(money1, money2)) {
			return money1;
		}
		Map<Double, Long> amounts = new HashMap<Double, Long>();
		amounts.put(money1, quantity1);
		amounts.put(money2, quantity2);
		return MoneyUtil.getWeightedAverage(amounts);
	}

	/**
	 * 金额格式化 逗号拼接
	 *
	 * @return
	 */
	public static String formatMoney(Object money, int bit) {
		try {
			if (money == null || money.toString() == "") {
				money = 0.0;
			}
			StringBuffer formatStr = new StringBuffer("#,###,##0");
			if (bit < 0) {
				bit = 0;
			}
			if (bit > 0) {
				formatStr.append(".");
			}
			for (int i = 0; i < bit; i++) {
				formatStr.append("#");
			}
			if (parseDouble(money).compareTo(0.0) > 0) {
				return numberFormat(parseDouble(money), formatStr.toString());
			} else {
				return "";
			}
		} catch (Exception e) {
		}
		return "";
	}

	public static Long parseLong(Object number) {
		if (number != null && number instanceof Number) {
			return ((Number) number).longValue();
		} else if (number != null && number instanceof BigDecimal) {
			return ((Number) number).longValue();
		} else if (number != null && StringUtil.isNotBlank(number.toString())) {
			return Long.parseLong(number.toString().trim());
		}
		return null;
	}
	
	public static Double parseDouble(Object number) {
		if (number != null && number instanceof Number) {
			return ((Number) number).doubleValue();
		} else if (number != null && number instanceof BigDecimal) {
			return ((Number) number).doubleValue();
		} else if (number != null && StringUtil.isNotBlank(number.toString())) {
			return Double.parseDouble(number.toString().trim());
		}
		return null;
	}

	public static String numberFormat(Object object, String format) {
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern(format);
		return myformat.format(object);
	}
}
