package a.f.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import a.f.utils.SystemUtils;

/**
 * ================================================
 * 类名：a.f.widget
 * 时间：2021/7/20 17:12
 * 描述：布局顶部状态栏自动填充 View 适用于界面顶部背景穿透布局
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class TopPaddingView extends View {

    private int mHeight;

    public TopPaddingView(Context context) {
        super(context);
        initHeight();
    }

    public TopPaddingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initHeight();
    }

    public TopPaddingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeight();
    }

    public TopPaddingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initHeight();
    }

    private void initHeight() {
        mHeight = SystemUtils.getStatusBarHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, mHeight);
    }

}