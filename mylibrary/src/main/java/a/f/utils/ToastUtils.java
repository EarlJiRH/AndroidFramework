package a.f.utils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import a.f.base.BaseApp;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:34
 * 描述：Toast 工具类；可在子线程直接使用
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ToastUtils {


    private static volatile ToastUtils mToastUtils; // 本类实例
    private Toast toast; // Toast 实例
    private Handler mHandler;

    private ToastUtils() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /** 获取 ToastUtils 的实例 */
    public static ToastUtils getInstance() {
        if (mToastUtils == null) {
            synchronized (ToastUtils.class) {
                if (mToastUtils == null) {
                    mToastUtils = new ToastUtils();
                }
            }
        }
        return mToastUtils;
    }

    // =============================================================================================

    /** 显示文本 Toast 默认短时间 【formatStringRId = 格式字符串资源ID】【args = 多个 内容/消息】 */
    public void showToast(int formatStringRId, Object... args) {
        showToast(BaseApp.getI().getString(formatStringRId), args);
    }

    /** 显示文本 Toast 默认短时间 【format = 格式】【args = 多个 内容/消息】 */
    public void showToast(String format, Object... args) {
        showToast(true, format, args);
    }

    /** 显示文本 Toast 【isShort = 是否短时间提示】【formatStringRId = 格式字符串资源ID】【args = 多个 内容/消息】 */
    public void showToast(boolean isShort, int formatStringRId, Object... args) {
        showToast(isShort, BaseApp.getI().getString(formatStringRId), args);
    }

    /** 显示文本 Toast 【isShort = 是否短时间提示】【format = 格式】【args = 多个 内容/消息】 */
    public void showToast(boolean isShort, String format, Object... args) {
        showToast(String.format(format, args), isShort);
    }

    // =============================================================================================

    /** 显示字符串资源 Toast 默认短时间提示 【stringRId = 字符串资源ID】 */
    public void showToast(int stringRId) {
        showToast(BaseApp.getI().getString(stringRId));
    }

    /** 显示文本 Toast 默认短时间提示 【content = 内容/消息】 */
    public void showToast(String content) {
        showToast(content, true);
    }

    /** 显示字符串资源 Toast 【stringRId = 字符串资源ID】【isShort = 是否短时间提示】 */
    public void showToast(int stringRId, boolean isShort) {
        showToast(BaseApp.getI().getString(stringRId), isShort);
    }

    /** 显示文本 Toast 【content = 内容/消息】【isShort = 是否是短提示】 */
    public void showToast(String content, boolean isShort) {
        showPrepare(content, null, null, 0, 0, isShort);
    }

    // =============================================================================================

    /** 显示自定义视图 Toast 【view = 自定义视图】【isShort = 是否短时间提示】 */
    public void showToast(View view, Integer gravity, int xOffset, int yOffset, boolean isShort) {
        showPrepare(null, view, gravity, xOffset, yOffset, isShort);
    }

    // =============================================================================================

    /** 准备显示 */
    private void showPrepare(String content, View view, Integer gravity, int xOffset, int yOffset, boolean isShort) {
        if (TextUtils.isEmpty(content) && view == null) {
            return;
        }

        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            showStart(content, view, gravity, xOffset, yOffset, isShort);
        } else if (mHandler != null) {
            mHandler.post(() -> showStart(content, view, gravity, xOffset, yOffset, isShort));
        }
    }

    /**
     * 开始显示
     *
     * @param gravity 显示位置 Just like Gravity.CENTER
     */
    private void showStart(String content, View view, Integer gravity, int xOffset, int yOffset, boolean isShort) {
        try {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(BaseApp.getI(), content, isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
            if (view != null) {
                toast.setView(view);
            }
            if (gravity != null) {
                toast.setGravity(gravity, xOffset, yOffset);
            }
            toast.show();
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

}
