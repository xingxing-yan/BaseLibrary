package com.yyx.baselib.utils;

import java.text.DecimalFormat;

public class StringUtils {

	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	public static String getMoney(float cost) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		return "¥" + decimalFormat.format(cost);
	}

}
