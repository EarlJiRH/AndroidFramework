package a.f.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AlertDialog;

import a.f.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * 类名：a.f.base
 * 时间：2021/7/20 16:30
 * 描述：基础 视图弹窗
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class BaseDialog extends AlertDialog implements DialogInterface.OnDismissListener {

    protected static final int DIALOG_MODE_NONE = 0; // 不做设置
    protected static final int DIALOG_MODE_TOP = 1; // 整体布局重心向上，左右撑满（PS:状态栏不会被覆盖）
    protected static final int DIALOG_MODE_BOTTOM = 2; // 整体布局重心向下，左右撑满

    private Unbinder mUnbinder;
    private View contentView;
    private int dialogMode;

    /** 默认不设置重力 构造函数 【layoutResID = 布局ID】 */
    public BaseDialog(Activity activity, int layoutResID) {
        super(activity, R.style.DialogBGT);
        contentView = LayoutInflater.from(activity).inflate(layoutResID, null);
        this.dialogMode = DIALOG_MODE_NONE;
        init(activity);
    }

    /** 自定义重力 构造函数 【layoutResID = 布局ID】【dialogMode = 布局模式】 */
    public BaseDialog(Activity activity, int layoutResID, int dialogMode) {
        super(activity, R.style.DialogBGT);
        contentView = LayoutInflater.from(activity).inflate(layoutResID, null);
        this.dialogMode = dialogMode;
        init(activity);
    }

    /** 自定义样式&重力 构造函数 【styleResID = 自定义样式 】【layoutResID = 布局ID】【dialogMode = 布局模式】 */
    public BaseDialog(Activity activity, int styleResID, int layoutResID, int dialogMode) {
        super(activity, styleResID);
        contentView = LayoutInflater.from(activity).inflate(layoutResID, null);
        this.dialogMode = dialogMode;
        init(activity);
    }

    /** 初始化参数 */
    private void init(Activity activity) {
        setOnDismissListener(this);
        setView(new EditText(activity)); // 解决输入框无法获取焦点问题
        mUnbinder = ButterKnife.bind(this, contentView);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);

        Window window = getWindow();
        if (window != null) {
            switch (dialogMode) {
                case DIALOG_MODE_NONE:
                    window.setGravity(Gravity.CENTER);
                    break;
                case DIALOG_MODE_TOP:
                    window.setGravity(Gravity.TOP);
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    break;
                case DIALOG_MODE_BOTTOM:
                    window.setGravity(Gravity.BOTTOM);
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public <T extends View> T findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        contentView = null;
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
    }
}
