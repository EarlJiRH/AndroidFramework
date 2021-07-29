package a.f.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:14
 * 描述：秒表 计时器；用于计算界面、方法等耗时情况；以小数毫秒为单位（0.001毫秒 = 1微秒）
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class StopWatch {

    private long startTime; // 开始时间点
    private long endTime; // 结束时间点
    private long frontTime; // 前一个时间点
    private List<Double> meteringTimes; // 所有计时集合，从先到后排序

    public StopWatch() {
        meteringTimes = new ArrayList<>();
    }

    /** 重置 */
    private void reset() {
        startTime = 0;
        endTime = 0;
        frontTime = 0;
        meteringTimes.clear();
    }

    /** 开始计时 */
    public void start() {
        reset();
        startTime = System.nanoTime();
        frontTime = startTime;
    }

    /** 计次 */
    public void metering() {
        endTime = System.nanoTime();
        meteringTimes.add(TimeUnit.NANOSECONDS.toMicros(endTime - frontTime) / 1000.0);
        frontTime = endTime;
    }

    /** 获取第一次计次时间；返回小数毫秒 */
    public double getMeteringTimeFirst() {
        return meteringTimes.get(0);
    }

    /** 获取总时间；返回小数毫秒 */
    public double getTotalTime() {
        return TimeUnit.NANOSECONDS.toMicros(endTime - startTime) / 1000.0;
    }

    /** 获取计次集合；返回毫秒集合 */
    public List<Double> getTotalTimeList() {
        return meteringTimes;
    }
}
