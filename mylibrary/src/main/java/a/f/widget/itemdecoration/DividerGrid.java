package a.f.widget.itemdecoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;

/**
 * ================================================
 * 类名：a.f.widget.itemdecoration
 * 时间：2021/7/20 18:05
 * 描述：RecyclerView 分割线 网格、瀑布流
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DividerGrid extends RecyclerView.ItemDecoration {

    private Drawable drawable;
    /**
     * 布局是否会增加&删除
     */
    private boolean isAddDelete;

    public DividerGrid(Context context) {
        this(context, false);
    }

    public DividerGrid(Context context, boolean isAddDelete) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{android.R.attr.listDivider});
        drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        this.isAddDelete = isAddDelete;
    }

    @Override
    public void onDraw(@NotNull Canvas c, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();

        if (!isAddDelete && isLastLine(parent, itemPosition, spanCount, childCount)) {
            // 如果是最后一行，则不需要绘制底部
            outRect.set(0, 0, drawable.getIntrinsicWidth(), 0);
        } else if (!isAddDelete && isLastColumn(parent, itemPosition, spanCount, childCount)) {
            // 如果是最后一列，则不需要绘制右边
            outRect.set(0, 0, 0, drawable.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
    }

    /**
     * 获取 列数
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 水平布局 画竖线
     */
    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int size = parent.getChildCount();
        for (int i = 0; i < size; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - layoutParams.leftMargin;
            int right = childView.getRight() + layoutParams.rightMargin + drawable.getIntrinsicWidth();
            int top = childView.getBottom() + layoutParams.bottomMargin;
            int bottom = top + drawable.getIntrinsicHeight();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }

    /**
     * 垂直布局 画横线
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        int size = parent.getChildCount();
        for (int i = 0; i < size; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - layoutParams.topMargin;
            int bottom = childView.getBottom() + layoutParams.bottomMargin;
            int left = childView.getRight() + layoutParams.rightMargin;
            int right = left + drawable.getIntrinsicWidth();
            drawable.setBounds(left, top, right, bottom);
            drawable.draw(c);
        }
    }

    /**
     * 是否 最后一行
     */
    private boolean isLastLine(RecyclerView parent, int itemPosition, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            childCount = childCount - childCount % spanCount;
            if (itemPosition >= childCount) {
                // 如果是最后一行，则不需要绘制底部
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 纵向滚动
                childCount = childCount - childCount % spanCount;
                if (itemPosition >= childCount) {
                    // 如果是最后一行，则不需要绘制底部
                    return true;
                }
            } else { // StaggeredGridLayoutManager 横向滚动
                if ((itemPosition + 1) % spanCount == 0) {
                    // 如果是最后一行，则不需要绘制底部
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否 最后一列
     */
    private boolean isLastColumn(RecyclerView parent, int itemPosition, int spanCount, int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((itemPosition + 1) % spanCount == 0) {
                // 如果是最后一列，则不需要绘制右边
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((itemPosition + 1) % spanCount == 0) {
                    // 如果是最后一列，则不需要绘制右边
                    return true;
                }
            } else {
                childCount = childCount - childCount % spanCount;
                if (itemPosition >= childCount) {
                    // 如果是最后一列，则不需要绘制右边
                    return true;
                }
            }
        }
        return false;
    }
}
