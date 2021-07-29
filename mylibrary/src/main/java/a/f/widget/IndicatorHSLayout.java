package a.f.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import a.f.utils.DisplayMetricsUtils;
import a.f.utils.SystemUtils;

/**
 * ================================================
 * 类名：a.f.widget
 * 时间：2021/7/20 17:13
 * 描述：下标指示器 容器布局控件
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class IndicatorHSLayout extends HorizontalScrollView {

    private List<ImageView> indicatorImageView; // 生成的下标控件集合
    private int resIdSelected; // 选中图片
    private int resIdUnselected; // 未选中图片
    private int marginLR; // 左右外边距

    public IndicatorHSLayout(Context context) {
        super(context);
        init();
    }

    public IndicatorHSLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorHSLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public IndicatorHSLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /** 初始化 */
    private void init() {
        setHorizontalScrollBarEnabled(false);
    }

    /**
     * 刷新布局；默认左右边距4dp，可自行扩展
     *
     * @param size            下标数量
     * @param resIdSelected   选中图片
     * @param resIdUnselected 未选中图片
     */
    public void initHSLayout(int size, int resIdSelected, int resIdUnselected) {
        initHSLayout(size, resIdSelected, resIdUnselected, 0, 4);
    }

    /**
     * 刷新布局
     *
     * @param size            下标数量
     * @param resIdSelected   选中图片
     * @param resIdUnselected 未选中图片
     * @param defaultSelected 默认选中的下标
     * @param marginLR        左右外边距；单位dp
     */
    public void initHSLayout(int size, int resIdSelected, int resIdUnselected, int defaultSelected, int marginLR) {
        try {
            this.resIdSelected = resIdSelected;
            this.resIdUnselected = resIdUnselected;
            this.marginLR = SystemUtils.dpToPx(marginLR);
            if (indicatorImageView == null) {
                indicatorImageView = new ArrayList<>();
            }
            indicatorImageView.clear();
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            for (int i = 0; i < size; i++) {
                LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                if (i == 0) {
                    imageViewLayoutParams.setMargins(this.marginLR * 2, 0, this.marginLR, 0);
                } else if (i == size - 1) {
                    imageViewLayoutParams.setMargins(this.marginLR, 0, this.marginLR * 2, 0);
                } else {
                    imageViewLayoutParams.setMargins(this.marginLR, 0, this.marginLR, 0);
                }

                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(imageViewLayoutParams);
                imageView.setImageResource(i == defaultSelected ? resIdSelected : resIdUnselected);

                linearLayout.addView(imageView);
                indicatorImageView.add(imageView);
            }

            removeAllViews();
            addView(linearLayout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 改变下标时 */
    public void onSelected(Activity activity, int position) {
        try {
            for (ImageView imageView : indicatorImageView) {
                imageView.setImageResource(resIdUnselected);
            }
            indicatorImageView.get(position).setImageResource(resIdSelected);

            int widths = 0;
            for (int i = 0; i < position; i++) {
                widths += indicatorImageView.get(i).getWidth() + (marginLR * 2);
            }
            widths += (indicatorImageView.get(position).getWidth() / 2) + (marginLR * 2);
            int leftScreen = widths - getScrollX();
            this.smoothScrollBy(leftScreen - DisplayMetricsUtils.getInstance(activity).getScreenWidthDiv(2), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
