package a.f.widget.xrview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;

/**
 * ================================================
 * 类名：a.f.widget.xrview
 * 时间：2021/7/20 17:54
 * 描述：自定义 TYRecyclerView 头部控件，解决继续下拉，刷新中的布局会逐渐变大，而且不会归位BUG
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class TYArrowRefreshHeader extends ArrowRefreshHeader {

    public TYArrowRefreshHeader(Context context) {
        super(context);
    }

    public TYArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /** 添加下拉高度限制 */
    @Override
    public void setVisibleHeight(int height) {
        int maxHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, Resources.getSystem().getDisplayMetrics());
        if (height > maxHeight) {
            height = maxHeight;
        }
        super.setVisibleHeight(height);
    }
}
