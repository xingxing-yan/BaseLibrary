package com.yyx.baselib.utils;

import java.util.Calendar;

public class DateUtils {
	/**
     * 通过年份和月份 得到当月的日子
     * 
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		int dateOfMonth = cal.getActualMaximum(Calendar.DATE);
		return dateOfMonth;
    }
    /**
     * 返回当前月份1号位于周几
     * @param year
     * 		年份
     * @param month
     * 		月份，传入系统获取的，不需要正常的
     * @return
     * 	日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(year, month, 1);
    	return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
	 * 获取当前时间
	 * @return
     */
	public static long getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		return calendar.getTimeInMillis();
	}
}
