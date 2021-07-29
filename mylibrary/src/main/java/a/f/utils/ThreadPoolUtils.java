package a.f.utils;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:35
 * 描述：简单的线程池工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ThreadPoolUtils extends ThreadPoolExecutor {

    /** 本类实例 */
    private static volatile ThreadPoolUtils mInstance;

    private ThreadPoolUtils() {
        super(1, 30, AFSF.TIME_1M, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
    }

    /** 获取 ThreadPoolUtils 的实例 */
    public static ThreadPoolUtils getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolUtils.class) {
                if (mInstance == null) {
                    mInstance = new ThreadPoolUtils();
                }
            }
        }
        return mInstance;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t != null) {
            t.printStackTrace();
        }
    }
}
