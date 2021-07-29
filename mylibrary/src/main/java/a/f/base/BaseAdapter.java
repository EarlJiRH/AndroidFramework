package a.f.base;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import a.f.utils.L;
import butterknife.ButterKnife;

/**
 * ================================================
 * 类名：a.f.base
 * 时间：2021/7/20 16:01
 * 描述：自定义 RecyclerView 分页 基础适配器
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, B> extends RecyclerView.Adapter<VH>  {

    protected Activity activity;
    private List<B> listDatas;
    private OnRVItemClickListener<B> onRVItemClickListener;
    private int headerNum; // 头部数量 适用于适配器删除和新增对下标的需求
    private int clickPosition; // 点击的下标

    public BaseAdapter(Activity activity) {
        this.activity = activity;
        this.listDatas = new ArrayList<>();
        this.clickPosition = 0;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        try {
            onBindViewHolder(holder, position, listDatas.get(position));
            registerListener(holder, position, listDatas.get(position));
            registerViewListener(holder, position, listDatas.get(position));
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

    /** 注册监听 */
    private void registerListener(RecyclerView.ViewHolder holder, int position, B bean) throws Exception {
        if (!isRegisterListener()) {
            return;
        }

        holder.itemView.setOnClickListener(view -> {
            try {
                if (onRVItemClickListener != null) {
                    clickPosition = clickPosition >= getItemCount() ? getItemCount() - 1 : clickPosition; // 防止刷新数据后导致下标越界
                    boolean isConsume = onClickSelect(clickPosition, listDatas.get(clickPosition), position, listDatas.get(position));
                    clickPosition = position;
                    if (!isConsume) {
                        onRVItemClickListener.onRVItemClick(position, bean);
                    }
                }
            } catch (Exception e) {
                L.writeExceptionLog(e);
            }
        });
        holder.itemView.setOnLongClickListener(view -> {
            try {
                if (onRVItemClickListener != null) {
                    onRVItemClickListener.onRVItemLongClick(position, bean);
                }
            } catch (Exception e) {
                L.writeExceptionLog(e);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return this.listDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, listDatas.get(position));
    }

    /** 设置默认选中的Item */
    protected void setDefaultClickPosition(int defaultClickPosition) {
        clickPosition = defaultClickPosition;
    }

    /** 获取当前选中的Item位置 */
    protected int getClickPosition() {
        return clickPosition;
    }

    /** 设置监听 */
    @SuppressWarnings("all")
    public <T extends BaseAdapter> T setOnRVItemClickListener(OnRVItemClickListener<B> onRVItemClickListener) {
        this.onRVItemClickListener = onRVItemClickListener;
        return (T) this;
    }

    /** 获取适配器数据集合 */
    public List<B> getListDatas() {
        return listDatas;
    }

    /** 获取指定Item对象 */
    public B getBean(int position) {
        return listDatas.size() > position ? listDatas.get(position) : null;
    }

    /** 替换指定Item对象 */
    public void setBean(int position, B bean) {
        this.listDatas.set(position, bean);
        notifyItemChanged(position + this.headerNum);
    }

    /** 是否是最后一个 */
    public boolean isLast(int position) {
        return position == listDatas.size() - 1;
    }

    /** 刷新数据 */
    public void onRefreshData(List<B> list) {
        this.listDatas.clear();
        if (list != null && !list.isEmpty()) {
            this.listDatas.addAll(list);
        }
        notifyDataSetChanged();
    }

    /** 加载更多数据 */
    public void onLoadMoreData(List<B> list) {
        this.listDatas.addAll(list);
        notifyDataSetChanged();
    }

    /** 清空数据 */
    public void onClearData() {
        onRefreshData(null);
    }

    /** 刷新指定数据 */
    public void onRefreshDesignateData(int position) {
        notifyItemChanged(position + this.headerNum);
    }

    /** 指定位置 item 插入新增数据 */
    public void onInsertedData(int position, B bean) {
        listDatas.add(position, bean);
        notifyItemInserted(position + this.headerNum);
        notifyItemRangeChanged(position + this.headerNum, listDatas.size());
    }

    /** 指定位置 item 删除数据 */
    public B onRemovedData(int position) {
        B bean = listDatas.remove(position);
        notifyItemRemoved(position + this.headerNum);
        notifyItemRangeChanged(position + this.headerNum, listDatas.size());
        return bean;
    }

    /** 指定位置 item 交换数据【交换位置后 position不可用，只能用bean】 */
    public void onMovedData(int fromPosition, int toPosition) {
        fromPosition += this.headerNum;
        toPosition += this.headerNum;
        listDatas.add(toPosition, listDatas.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    /** 设置头部数量；适用于适配器删除和新增对下标的需求 */
    public void setAdapterHeaderNum(int headerNum) {
        this.headerNum = headerNum;
    }

    /** 是否有数据/是否有Item */
    public boolean isHaveData() {
        return listDatas.size() > 0;
    }

    public abstract void onBindViewHolder(VH holder, int position, B bean);

    /** 是否注册Item监听 */
    public boolean isRegisterListener() {
        return true;
    }

    /** 注册 Item 内部 View 监听 */
    public void registerViewListener(VH holder, int position, B bean) {
    }

    /**
     * 单选或多选 Item
     *
     * @return 是否消耗事件，消耗后不会调用[onRVItemClickListener.onRVItemClick(position, bean)]
     */
    public boolean onClickSelect(int oldPosition, B oldBean, int newPosition, B newBean) {
        return false;
    }

    /** 获取Item类型，适用于多布局 */
    public int getItemViewType(int position, B bean) {
        return 0;
    }

    /** 已实现自动绑定View 自定义 ViewHolder */
    public abstract static class TYViewHolder extends RecyclerView.ViewHolder {
        public TYViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /** RecyclerView Item 监听事件回调 */
    public interface OnRVItemClickListener<B> {
        /** RecyclerView Item 单击 */
        default void onRVItemClick(int position, B bean) {
        }

        /** RecyclerView Item 长按 */
        default void onRVItemLongClick(int position, B bean) {
        }
    }

}
