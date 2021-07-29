package a.f.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:21
 * 描述：数字 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class NumberUtils {

    /** 对象转 byte 【转换失败 = 0】 */
    public static byte objToByte(Object value) {
        return objToByte(value, (byte) 0);
    }

    /** 对象转 byte 【defValue = 默认参数】 */
    public static byte objToByte(Object value, byte defValue) {
        try {
            return Byte.valueOf(String.valueOf(value));
        } catch (Exception e) {
        }
        return defValue;
    }

    /** 对象转 Int 【转换失败 = 0】 */
    public static int objToInt(Object value) {
        return objToInt(value, 0);
    }

    /** 对象转 Int 【defValue = 默认参数】 */
    public static int objToInt(Object value, int defValue) {
        try {
            return Integer.valueOf(String.valueOf(value));
        } catch (Exception e) {
        }
        return defValue;
    }

    /** 对象转 Long 【转换失败 = 0】 */
    public static long objToLong(Object value) {
        return objToLong(value, 0);
    }

    /** 对象转 Long 【defValue = 默认参数】 */
    public static long objToLong(Object value, long defValue) {
        try {
            return Long.valueOf(String.valueOf(value));
        } catch (Exception e) {
        }
        return defValue;
    }

    /** 对象转 Float 【转换失败 = 0】 */
    public static float objToFloat(Object value) {
        return objToFloat(value, 0);
    }

    /** 对象转 Float 【defValue = 默认参数】 */
    public static float objToFloat(Object value, float defValue) {
        try {
            return Float.valueOf(String.valueOf(value));
        } catch (Exception e) {
        }
        return defValue;
    }

    /** 对象转 Double 【转换失败 = 0】 */
    public static double objToDouble(Object value) {
        return objToDouble(value, 0);
    }

    /** 对象转 Double 【defValue = 默认参数】 */
    public static double objToDouble(Object value, double defValue) {
        try {
            return Double.valueOf(String.valueOf(value));
        } catch (Exception e) {
        }
        return defValue;
    }

    /** 计算 大型 浮点数字 除法 【divisor = 除数】【dividend = 被除数】【digits = 小数点多少位】 */
    public static BigDecimal calculateDiv(String divisor, String dividend, int digits) {
        return new BigDecimal(divisor).divide(new BigDecimal(dividend), digits, BigDecimal.ROUND_HALF_UP);
    }

    /** 计算 大型 浮点数字 乘法 【multiplier = 乘数】【multiplicand = 被乘数】 */
    public static BigDecimal calculateMul(String multiplier, String multiplicand) {
        return new BigDecimal(multiplier).multiply(new BigDecimal(multiplicand));
    }

    /** 数字 格式化 */
    public static String numberFormat(String format, double value) {
        return new DecimalFormat(format).format(value);
    }

    /** 数字 格式化 */
    public static String numberFormat(String format, String value) {
        return new DecimalFormat(format).format(Double.valueOf(value));
    }
}
