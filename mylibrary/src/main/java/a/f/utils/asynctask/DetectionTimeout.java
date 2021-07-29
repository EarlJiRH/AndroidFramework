package a.f.utils.asynctask;

import android.os.Handler;

/**
 * ================================================
 * 类名：a.f.utils.asynctask
 * 时间：2021/7/20 17:40
 * 描述：异步任务工具 检测超时 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DetectionTimeout {

    private Handler handler;
    private Runnable runnable;

    /** 检测超时 */
    public void detectionTimeout(Handler handler, TimeoutAsyncTask timeoutAsyncTask, long time) {
        this.handler = handler;
        runnable = () -> {
            timeoutAsyncTask.cancel(true);
            timeoutAsyncTask.taskTimeout();
        };
        handler.postDelayed(runnable, time);
    }

    /** 关闭检测 */
    public void detectionEnd() {
        handler.removeCallbacks(runnable);
        handler = null;
        runnable = null;
    }
}
