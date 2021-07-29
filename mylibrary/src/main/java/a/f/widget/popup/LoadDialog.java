package a.f.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import a.f.R;
import a.f.R2;
import a.f.base.BaseApp;
import a.f.base.BaseDialog;
import a.f.utils.constant.AFSF;
import butterknife.BindView;

/**
 * ================================================
 * 类名：a.f.widget.popup
 * 时间：2021/7/20 17:56
 * 描述：加载中 弹窗
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class LoadDialog extends BaseDialog {
    /** 本类实例 */
    private static LoadDialog mLoadDialog;
    private Runnable mRunnable = LoadDialog::dismissLoadDialog;
    @BindView(R2.id.loadGifView)
    ImageView loadGifView;
    @BindView(R2.id.loadHint)
    TextView loadHint;

    public LoadDialog(Activity activity, String hint, boolean isFinish) {
        super(activity, R.layout.dialog_load);
        Glide.with(loadGifView)
                .load(AFSF.ANDROID_RESOURCE + R.raw.load)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(loadGifView);
        if (TextUtils.isEmpty(hint)) {
            loadHint.setVisibility(View.GONE);
        } else {
            loadHint.setText(hint);
            loadHint.setVisibility(View.VISIBLE);
        }

        setCanceledOnTouchOutside(false);
        setOnCancelListener(dialog -> {
            if (isFinish) {
                activity.finish();
            }
        });
    }


    /** 显示弹窗 无提示需关闭 */
    public synchronized static void showLoadDialog(Activity activity) {
        showLoadDialog(activity, null, true, AFSF.TIME_30S);
    }

    /** 显示弹窗 可提示需关闭 */
    public synchronized static void showLoadDialog(Activity activity, String hint) {
        showLoadDialog(activity, hint, true, AFSF.TIME_30S);
    }

    /** 显示弹窗 无提示可关闭 */
    public synchronized static void showLoadDialog(Activity activity, boolean isFinish) {
        showLoadDialog(activity, null, isFinish, AFSF.TIME_30S);
    }

    /** 显示弹窗 无提示需关闭自定义时间 */
    public synchronized static void showLoadDialog(Activity activity, long delayMillis) {
        showLoadDialog(activity, null, true, delayMillis);
    }

    /** 显示弹窗 可提示可关闭 */
    public synchronized static void showLoadDialog(Activity activity, String hint, boolean isFinish, long delayMillis) {
        dismissLoadDialog();
        mLoadDialog = new LoadDialog(activity, hint, isFinish);
        mLoadDialog.show();
        BaseApp.getI().mHandler.postDelayed(mLoadDialog.mRunnable, delayMillis);
    }

    /** 关闭弹窗 */
    public synchronized static void dismissLoadDialog() {
        if (mLoadDialog != null) {
            BaseApp.getI().mHandler.removeCallbacks(mLoadDialog.mRunnable);
            mLoadDialog.dismiss();
            mLoadDialog = null;
        }
    }

    /** 获取拥有此弹窗的上下文对象 */
    public synchronized static Context getOwnerContext() {
        return mLoadDialog == null ? null : mLoadDialog.getContext();
    }
}
