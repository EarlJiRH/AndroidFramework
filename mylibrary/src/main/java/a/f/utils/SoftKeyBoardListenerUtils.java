package a.f.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:15
 * 描述：键盘弹出&收起监听 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SoftKeyBoardListenerUtils implements ViewTreeObserver.OnGlobalLayoutListener {

    private View mRootView; // Activity的根视图
    private OnSoftKeyBoardChangeListener mOnSoftKeyBoardChangeListener;
    private int mRootViewVisibleHeight; // 记录根视图的显示高度

    @Override
    public void onGlobalLayout() {
        if (mRootView == null) {
            return;
        }

        Rect r = new Rect(); // 获取当前根视图在屏幕上显示的大小
        mRootView.getWindowVisibleDisplayFrame(r);
        int visibleHeight = r.height();

        if (mRootViewVisibleHeight == 0) {
            mRootViewVisibleHeight = visibleHeight;
            return;
        }

        // 根视图显示高度没有变化，可以看作软键盘显示/隐藏状态没有改变
        if (mRootViewVisibleHeight == visibleHeight) {
            return;
        }

        // 根视图显示高度变小超过200，可以看作软键盘显示了
        if (mRootViewVisibleHeight - visibleHeight > 200) {
            if (mOnSoftKeyBoardChangeListener != null) {
                mOnSoftKeyBoardChangeListener.onSoftKeyBoardShow(mRootViewVisibleHeight - visibleHeight);
            }
            mRootViewVisibleHeight = visibleHeight;
            return;
        }

        //根视图显示高度变大超过200，可以看作软键盘隐藏了
        if (visibleHeight - mRootViewVisibleHeight > 200) {
            if (mOnSoftKeyBoardChangeListener != null) {
                mOnSoftKeyBoardChangeListener.onSoftKeyBoardHide(visibleHeight - mRootViewVisibleHeight);
            }
            mRootViewVisibleHeight = visibleHeight;
            return;
        }
    }

    /** 设置键盘状态监听 */
    public void setOnSoftKeyBoardChangeListener(Activity activity, OnSoftKeyBoardChangeListener listener) {
        mOnSoftKeyBoardChangeListener = listener;
        mRootView = activity.getWindow().getDecorView(); // 获取Activity的根视图
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /** 删除键盘状态监听 */
    public void deleteOnSoftKeyBoardChangeListener() {
        mOnSoftKeyBoardChangeListener = null;
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        mRootView = null;
    }

    /** 键盘弹出&收起 监听器 */
    public interface OnSoftKeyBoardChangeListener {
        /** 键盘处于打开状态 */
        default void onSoftKeyBoardShow(int height) {
        }

        /** 键盘处于关闭状态 */
        default void onSoftKeyBoardHide(int height) {
        }
    }
}
