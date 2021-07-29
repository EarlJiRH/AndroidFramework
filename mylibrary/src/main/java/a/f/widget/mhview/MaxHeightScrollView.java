package a.f.widget.mhview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ScrollView;

import a.f.R;
import a.f.utils.SystemUtils;

/**
 * ================================================
 * 类名：a.f.widget.mhview
 * 时间：2021/7/20 18:04
 * 描述：自定义有最大高度属性 ScrollView
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class MaxHeightScrollView extends ScrollView {

    /** 最大高度，单位像素 */
    private int maxHeight;

    /** 设置最大高度 【maxHeight = 最大高度；单位DP】 */
    public void setMaxHeight(int maxHeight) {
        setMaxHeight(maxHeight, true);
    }

    /** 设置最大高度 【maxHeight = 最大高度；单位DP】【isInvalidate = 是否刷新View】 */
    public void setMaxHeight(int maxHeight, boolean isInvalidate) {
        this.maxHeight = SystemUtils.dpToPx(maxHeight);
        if (isInvalidate) {
            invalidate();
        }
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public MaxHeightScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    /** 初始化样式参数 */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightScrollView);
        this.maxHeight = typedArray.getDimensionPixelSize(R.styleable.MaxHeightScrollView_maxHeight, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
