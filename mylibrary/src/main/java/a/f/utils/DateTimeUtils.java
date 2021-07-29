package a.f.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:27
 * 描述：日期时间 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DateTimeUtils {
    // =============================================================================================

    /** 获取设备当前时间文本 【format = 格式】 */
    public static String getNowTime(String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date());
    }

    /** 获取设备当前时间戳 */
    public static long getNowTime() {
        return getTime(0);
    }

    /** 获取指定时间戳 【salt = 需要 加or减 的数；不需加减则传0；加传正数；减传负数】 */
    public static long getTime(long salt) {
        return System.currentTimeMillis() + salt;
    }

    // =============================================================================================

    /** 字符串日期 TO 时间戳 */
    public static long getDateToTimestamp(String format, String date) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /** 时间戳 TO 字符串日期 */
    public static String getTimestampToDate(String format, long timestamp) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(timestamp);
    }

    // =============================================================================================

    /** 整型 年月 TO 字符串日期 */
    public static String getTimesToDate(String format, int year, int month) {
        return getTimesToDate(format, year, month, 1);
    }

    /** 整型 年月日 TO 字符串日期 */
    public static String getTimesToDate(String format, int year, int month, int day) {
        return getTimesToDate(format, year, month, day, 0);
    }

    /** 整型 年月日时 TO 字符串日期 */
    public static String getTimesToDate(String format, int year, int month, int day, int hourOfDay) {
        return getTimesToDate(format, year, month, day, hourOfDay, 0);
    }

    /** 整型 年月日时分 TO 字符串日期 */
    public static String getTimesToDate(String format, int year, int month, int day, int hourOfDay, int minute) {
        return getTimesToDate(format, year, month, day, hourOfDay, minute, 0);
    }

    /** 整型 年月日时分秒 TO 字符串日期 */
    public static String getTimesToDate(String format, int year, int month, int day, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(year, month - 1, day, hourOfDay, minute, second);
        return getTimesToDate(format, calendar);
    }

    /** 自定义日历对象 TO 字符串日期 */
    public static String getTimesToDate(String format, Calendar calendar) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(calendar.getTime());
    }

    // =============================================================================================

    /** 整型 年月 TO 时间戳 */
    public static long getTimesToTimestamp(int year, int month) {
        return getTimesToTimestamp(year, month, 1);
    }

    /** 整型 年月日 TO 时间戳 */
    public static long getTimesToTimestamp(int year, int month, int day) {
        return getTimesToTimestamp(year, month, day, 0);
    }

    /** 整型 年月日时 TO 时间戳 */
    public static long getTimesToTimestamp(int year, int month, int day, int hourOfDay) {
        return getTimesToTimestamp(year, month, day, hourOfDay, 0);
    }

    /** 整型 年月日时分 TO 时间戳 */
    public static long getTimesToTimestamp(int year, int month, int day, int hourOfDay, int minute) {
        return getTimesToTimestamp(year, month, day, hourOfDay, minute, 0);
    }

    /** 整型 年月日时分秒 TO 时间戳 */
    public static long getTimesToTimestamp(int year, int month, int day, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(year, month - 1, day, hourOfDay, minute, second);
        return calendar.getTimeInMillis();
    }

    // =============================================================================================

    /** 对比日期差；返回 是否超过指定值 */
    public static boolean compareDateDifference(String format, String startTime, String endTime, long difference) {
        long startTimeL = getDateToTimestamp(format, startTime);
        long endTimeL = getDateToTimestamp(format, endTime);
        return compareDateDifference(startTimeL, endTimeL, difference);
    }

    /** 对比日期差；返回 是否超过指定值 */
    public static boolean compareDateDifference(long startTime, long endTime, long difference) {
        return endTime - startTime > difference;
    }

    // =============================================================================================

    /** 对比时间顺序；返回 是否正序 */
    public static boolean compareSequence(String format, String startTime, String endTime) {
        return compareSequence(format, startTime, endTime, true);
    }

    /** 对比时间顺序；返回 是否正序；needEqual 是否等于 */
    public static boolean compareSequence(String format, String startTime, String endTime, boolean needEqual) {
        if (needEqual) {
            return getDateToTimestamp(format, startTime) <= getDateToTimestamp(format, endTime);
        } else {
            return getDateToTimestamp(format, startTime) < getDateToTimestamp(format, endTime);
        }
    }

    /** 获取某天时间点 【timestamp = 某天】【timePoint = 时间点/小时、分钟、秒、毫秒】 */
    public static long getSomedayPoint(long timestamp, long timePoint) {
        String date = getTimestampToDate(AFSF.DT_003, timestamp);
        return getDateToTimestamp(AFSF.DT_003, date) + timePoint;
    }

    /***
     * 获取总时间字符串
     * @param different 时间差值
     * @return 计算的时间【例子：1天2时3分5秒】
     */
    public static String getTotalDateString(long different) {
        return getTotalDateString(0, different);
    }

    /***
     * 获取总时间字符串
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 计算的时间【例子：1天2时3分5秒】
     */
    public static String getTotalDateString(long startTime, long endTime) {
        if (startTime > endTime) {
            return "";
        }

        long different = endTime - startTime;
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;
        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;
        long elapsedSeconds = different / secondsInMilli;

        StringBuilder sb = new StringBuilder();
        if (elapsedDays > 0) {
            sb.append(elapsedDays).append("天");
        }
        if (elapsedHours > 0) {
            sb.append(elapsedHours).append("时");
        }
        if (elapsedMinutes > 0) {
            sb.append(elapsedMinutes).append("分");
        }
        if (elapsedSeconds > 0 || TextUtils.isEmpty(sb)) {
            sb.append(elapsedSeconds).append("秒");
        }

        return sb.toString();
    }
}
