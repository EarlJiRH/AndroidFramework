package a.f.utils;

import androidx.annotation.NonNull;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:33
 * 描述：记录 未捕获异常 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class UnCapturedExceptionRecord implements Thread.UncaughtExceptionHandler{


    private static volatile UnCapturedExceptionRecord mUnCapturedExceptionRecord; // 本类实例
    private Thread.UncaughtExceptionHandler oldHandler;

    private UnCapturedExceptionRecord() {
    }

    /** 获取 UnCapturedExceptionRecord 的实例 */
    public static UnCapturedExceptionRecord getInstance() {
        if (mUnCapturedExceptionRecord == null) {
            synchronized (UnCapturedExceptionRecord.class) {
                if (mUnCapturedExceptionRecord == null) {
                    mUnCapturedExceptionRecord = new UnCapturedExceptionRecord();
                }
            }
        }
        return mUnCapturedExceptionRecord;
    }

    /** 初始化捕获 未捕获异常服务 */
    public void init() {
        oldHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
        L.writeExceptionLog(false, throwable);
        if (oldHandler != null) {
            oldHandler.uncaughtException(thread, throwable);
        }
    }
}
