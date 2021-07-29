package a.f.widget.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.f.R;
import a.f.bean.common.judgement.SelectBean;
import a.f.utils.AFVariableUtils;
import a.f.utils.callback.CallBackValue;

/**
 * ================================================
 * 类名：a.f.widget.popup
 * 时间：2021/7/20 18:03
 * 描述：挑选弹窗 PopupWindow
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SelectPopup extends PopupWindow implements View.OnClickListener {

    private boolean isHaveData;
    private CallBackValue mCallBackValue;

    public SelectPopup(Activity activity, int maxWidth, List<SelectBean> list, OnSettingViewCallBack onSettingViewCallBack, CallBackValue callBackValue, PopupWindow.OnDismissListener onDismissListener) {
        try {
            list = list == null ? new ArrayList<>() : list;
            this.isHaveData = !list.isEmpty();
            this.mCallBackValue = callBackValue;
            this.setOnDismissListener(onDismissListener);

            View rootView = LayoutInflater.from(activity).inflate(R.layout.popup_select, null);
            setContentView(rootView);
            setWidth(maxWidth);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            setFocusable(true);
            setOutsideTouchable(true);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setAnimationStyle(R.style.AnimSelectPopup);

            LinearLayout pSelectItemLayout = rootView.findViewById(R.id.pSelectItemLayout);
            for (int i = 0; i < list.size(); i++) {
                SelectBean selectBean = list.get(i);
                View itemView = LayoutInflater.from(activity).inflate(R.layout.item_select, null);
                TextView selectItemText = itemView.findViewById(R.id.selectItemText);
                selectItemText.setText(selectBean.getTitle());
                selectItemText.setTag(selectBean);
                selectItemText.setOnClickListener(this);
                if (onSettingViewCallBack != null) {
                    onSettingViewCallBack.onSettingView(i, selectBean, selectItemText);
                }
                itemView.findViewById(R.id.selectItemLine).setVisibility(i == 0 ? View.GONE : View.VISIBLE);
                pSelectItemLayout.addView(itemView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (mCallBackValue != null) {
                mCallBackValue.onBack(AFVariableUtils.getListForAllType(v.getTag()));
            }
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 显示 List PopupWindow 默认 */
    public void showPopupWindow(View view) {
        showPopupWindow(view, 0, 0);
    }

    /** 显示 List PopupWindow */
    public void showPopupWindow(View view, int xoff, int yoff) {
        if (!isShowing() && isHaveData) {
            showAsDropDown(view, xoff, yoff);
        } else {
            dismiss();
        }
    }

    /** 设置文本控件回调接口 */
    public interface OnSettingViewCallBack {
        /** 设置文本控件回调 */
        void onSettingView(int index, SelectBean selectBean, TextView textView);
    }

}
