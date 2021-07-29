package a.f.utils.asynctask;

import android.os.AsyncTask;

/**
 * ================================================
 * 类名：a.f.utils.asynctask
 * 时间：2021/7/20 17:40
 * 描述：自定义控制超时 异步任务 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */


public abstract class TimeoutAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    /** 超时回调 */
    public void taskTimeout() {
    }

}
