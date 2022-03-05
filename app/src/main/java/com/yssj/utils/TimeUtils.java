package com.yssj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.yssj.activity.R;

import android.content.Context;
import android.text.TextUtils;

public class TimeUtils {
	
	private static Calendar calendar = Calendar.getInstance();
	private static SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
	
	
	public static String formatTimeFuzzy(Context context, long timeInMillis) {
		return formatTimeFuzzy(context, timeInMillis, false, null);
	}
	
	public static String formatTimeFuzzy(Context context, long timeInMillis, boolean isSuperFuzzy, String defaultTime) {
		String[] pattern = context.getResources().getStringArray(R.array.time_pattern);
		if (!TextUtils.isEmpty(defaultTime)) {
			pattern[5] = defaultTime;
		}
		String result;
		
		// 先计算昨天的时间
		final long currentTime = System.currentTimeMillis();
		calendar.setTimeInMillis(currentTime);
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH);
		final int day = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.clear();
		calendar.set(year, month, day);
		final long yesterdayInMillis = calendar.getTimeInMillis();
		
		// 比较指定日期和昨天的时间
		// 如果昨天的时间比指定的日期还小的话
		// 说明指定日期还在今天，否则是昨天的
		if (yesterdayInMillis < timeInMillis) {
			long delta = currentTime - timeInMillis;
			if (delta <= 3600 * 1000) {
				// 如果没有超过一个小时 eg: 刚刚 or 7分钟前
				final long mins = delta / (1000 * 60);
				if (mins <= 0) {
					result = pattern[0];
				} else {
					result = String.format(pattern[1], mins);
				}
			} else if (delta <= 86400 * 1000) {
				// 如果没有超过一天 eg: 2小时前
				final long hours = Math.max(1, (delta / (1000 * 3600)));
				result = String.format(pattern[2], hours);
			} else {
				throw new IllegalArgumentException("什么，你竟然把时间指定到了明天!?");
			}
		} else if (isSuperFuzzy) {
			final long aDay = 86400 * 1000;
			final long theDayBeforeYesterday = yesterdayInMillis - aDay;
			final long threeDaysAgo = theDayBeforeYesterday - aDay;
			long overTime = -1;
			if (yesterdayInMillis >= timeInMillis && timeInMillis > theDayBeforeYesterday) {
				// 昨天的范围
				result = pattern[3];
				overTime = yesterdayInMillis - timeInMillis;
			} else if (theDayBeforeYesterday >= timeInMillis && timeInMillis > threeDaysAgo) {
				// 前天的范围
				result = pattern[4];
				overTime = theDayBeforeYesterday - timeInMillis;
			} else {
				dateFormat.applyPattern(pattern[5]);
				result = dateFormat.format(new Date(timeInMillis));
				return result;
			}
			
			// 计算多出来的时间，然后赋予模糊描述
			if (overTime != -1) {
				overTime = Math.max(0, overTime / (3600 * 1000));
				if (overTime >= 0 && overTime < 6) {			// 往后推6个小时之内，就是晚上
					result += pattern[12];
				} else if (overTime >= 6 && overTime < 7) {		// 再往后推1个小时之内，就是傍晚
					result += pattern[11];
				} else if (overTime >= 7 && overTime < 11) {	// 再往后推4个小时之内，就是下午
					result += pattern[10];
				} else if (overTime >= 11 && overTime < 13) {	// 再往后推2个小时之内，就是中午
					result += pattern[9];
				} else if (overTime >= 13 && overTime < 18) {	// 再往后推5个小时之内，就是上午
					result += pattern[8];
				} else if (overTime >= 18 && overTime < 19) {	// 再往后推1个小时之内，就是清晨
					result += pattern[7];
				} else if (overTime >= 19 && overTime <= 24) {	// 再往后推的所有时间都是凌晨
					result += pattern[6];
				}
			}
		} else {
			dateFormat.applyPattern(pattern[5]);
			result = dateFormat.format(new Date(timeInMillis));
			return result;
		}
				
		return result;
		
	}
	
	 public static int dayForWeek(String pTime){
//		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////		  LogYiFu.e("yyyy-MM-dd", format.format(new Date(Long.parseLong(pTime)))+"");
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date(Long.parseLong(pTime)));
		  int dayForWeek = 0;
		  if(c.get(Calendar.DAY_OF_WEEK) == 1){
		   dayForWeek = 7;
		  }else{
		   dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		  }
		  return dayForWeek;
	 }
	/**
	 * 
	 * @param long_add_date
	 * @return
	 */
	 public static String showSendTime(long longSendTime) {

			long timeMillis = new Date().getTime() - longSendTime;
			if (timeMillis < 3600000) {// 一个小时之内
				long showTimeMin = timeMillis / 60000;
				if (showTimeMin < 1) {
					return "刚刚";
				} else
					return showTimeMin + "分钟前";
			} else if (timeMillis >= 3600000 && timeMillis < 3600000 * 24) {
				long showTimeHour = timeMillis / 3600000;
				return showTimeHour + "小时前";
			} else {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date(longSendTime);
					date = sdf.parse(sdf.format(date));
					Date now = new Date();
					now = sdf.parse(sdf.format(now));
					long sl = date.getTime();
					long el = now.getTime();
					long ei = sl - el;
					int value = (int) (ei / (1000 * 60 * 60 * 24));
					SimpleDateFormat sdfDay = new SimpleDateFormat("HH:mm");
					String dayTime = sdfDay.format(new Date(longSendTime));
					SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
					SimpleDateFormat sdfThisYear = new SimpleDateFormat("MM月dd日 HH:mm");
//					if (value == 0) {
//						return "今天" +dayTime;
//					} else if (value == -1) {
//						return "昨天" +dayTime;
//					} else if(value == -2){
//						return "前天" +dayTime;
//					}else{
						//是否是否是今年
						boolean isThisYear = sdfYear.format(new Date()).equals(sdfYear.format(new Date(longSendTime)));
						if(isThisYear){
							return sdfThisYear.format(new Date(longSendTime));
						}else {
							return sdf.format(new Date(longSendTime));
						}
//					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			return "";
		}
}
