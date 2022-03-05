package com.yssj.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    /**
     * 计算两个日期型的时间相差多少时间
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static String twoDateDistance(Date startDate, Date endDate) {

        if (startDate == null || endDate == null) {
            return null;
        }
        long timeLong = endDate.getTime() - startDate.getTime();
        if (timeLong < 60 * 1000)
            // return timeLong / 1000 + "秒前";
            return "刚刚";
        else if (timeLong < 60 * 60 * 1000) {
            timeLong = timeLong / 1000 / 60;
            return timeLong + "分钟前";
        } else if (timeLong < 60 * 60 * 24 * 1000) {
            timeLong = timeLong / 60 / 60 / 1000;
            return timeLong + "小时前";
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7) {
            timeLong = timeLong / 1000 / 60 / 60 / 24;
            return timeLong + "天前";
        } else if (timeLong < 60 * 60 * 24 * 1000 * 7 * 4) {
            timeLong = timeLong / 1000 / 60 / 60 / 24 / 7;
            return timeLong + "周前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.format(startDate);
        }
    }

    /**
     * 将毫秒数格式化
     *
     * @param millisecond 毫秒数
     * @return
     */
    public static String FormatMillisecond(long millisecond) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        return sdf.format(date);
    }

    public static String FormatMillisecondForDay(long millisecond) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(millisecond);
        return sdf.format(date);
    }

    /**
     * 将毫秒数格式化
     *
     * @param millisecond 毫秒数
     * @return
     */
    public static String FormatMill(long millisecond) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(millisecond);
        return sdf.format(date);
    }

    /**
     * 将毫秒数格式为为剩余多少时间
     *
     * @param mss
     * @return
     */
    public static String FormatMilliseondToEndTime(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
    }


    public static String FormatMilliseondToEndTime2(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        String h = hours >= 10 ? hours+"" : "0"+hours;
        String m = minutes >= 10 ? minutes+"" : "0"+minutes;
        String s = seconds >= 10 ? seconds+"" : "0"+seconds;


        return   h + ":" + m + ":" + s;
    }

    /**
     * 将当天的毫秒值转为上下午
     *
     * @param time
     * @return
     */
    public static String formatTimeDangTian(long time) {
        String reTime = "";
        // time = time +8*24*60*60*1000;
        // // 当天
        // Integer ss = 1000;
        // Integer mi = ss * 60;
        // Integer hh = mi * 60;
        // Integer dd = hh * 24;
        //
        // Long day = time / dd;
        // Long hour = (time - day * dd) / hh;
        // Long minute = (time - day * dd - hour * hh) / mi;
        //

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Date date = new Date(time);
        reTime = sdf.format(date);
        LogYiFu.e("GMT", reTime);

        int hour = Integer.parseInt(reTime.split(":")[0]);
        String minute = reTime.split(":")[1];

        // 上午
        if (hour < 12) {
            reTime = "上午" + hour + ":" + minute;

        } else {// 下午
            long hourPM = hour - 12;

            if (hourPM == 0) {
                reTime = "下午" + 12 + ":" + minute;
            } else {
                reTime = "下午" + hourPM + ":" + minute;
            }


        }

        return reTime;
    }

    /**
     * 将long转换为日期（yyyy-MM-dd HH:mm）
     *
     * @return 到天
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDay() {
        String day = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            day = sDateFormat.format(new Date(System.currentTimeMillis() + 0));
        } catch (Exception e) {

        }
        return day;
    }


    public static boolean isSameWeek(Date d1, Date d2) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Date d1 = null;
//		Date d2 = null;
//		try{
//			d1 = format.parse(date1);
//			d2 = format.parse(date2);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        //subYear==0,说明是同一年
        if (subYear == 0) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        //例子:cal1是"2005-1-1"，cal2是"2004-12-25"
        //java对"2004-12-25"处理成第52周
        // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
        //大家可以查一下自己的日历
        //处理的比较好
        //说明:java的一月用"0"标识，那么12月用"11"
        else if (subYear == 1 && cal2.get(Calendar.MONTH) == Calendar.DECEMBER) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        //例子:cal1是"2004-12-31"，cal2是"2005-1-1"
        else if (subYear == -1 && cal1.get(Calendar.MONTH) == Calendar.DECEMBER) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }


    /*
   * 将时间转换为时间戳
   */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }


    /*
 * 将时间戳转换为时间
 */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

}
