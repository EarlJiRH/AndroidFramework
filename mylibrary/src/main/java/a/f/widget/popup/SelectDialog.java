package a.f.widget.popup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import a.f.R;
import a.f.R2;
import a.f.base.BaseDialog;
import a.f.bean.common.judgement.SelectBean;
import a.f.utils.AFVariableUtils;
import a.f.utils.callback.CallBackValue;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * ================================================
 * 类名：a.f.widget.popup
 * 时间：2021/7/20 18:03
 * 描述：挑选弹窗
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SelectDialog extends BaseDialog {

    @BindView(R2.id.selectItemLayout)
    LinearLayout selectItemLayout;

    private CallBackValue callBackValue;
    private View.OnClickListener mOnClickListener = this::onViewClicked;

    public SelectDialog(Activity activity, List<SelectBean> list, CallBackValue callBackValue) {
        super(activity, R.layout.dialog_select, DIALOG_MODE_BOTTOM);
        list = list == null ? new ArrayList<>() : list;
        this.callBackValue = callBackValue;

        try {
            init(activity, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 初始化 */
    private void init(Activity activity, List<SelectBean> list) {
        for (int i = 0; i < list.size(); i++) {
            SelectBean selectBean = list.get(i);
            View view = LayoutInflater.from(activity).inflate(R.layout.item_select, null);
            TextView selectItemText = view.findViewById(R.id.selectItemText);
            selectItemText.setText(selectBean.getTitle());
            selectItemText.setTag(selectBean);
            selectItemText.setOnClickListener(mOnClickListener);
            view.findViewById(R.id.selectItemLine).setVisibility(i == 0 ? View.GONE : View.VISIBLE);
            selectItemLayout.addView(view);
        }
    }

    @OnClick({R2.id.selectCancel})
    public void onViewClicked(View view) {
        try {
            if (view.getId() == R.id.selectCancel) {
                SelectDialog.this.dismiss();
            } else {
                SelectBean selectBean = (SelectBean) view.getTag();
                if (callBackValue != null) {
                    callBackValue.onBack(AFVariableUtils.getListForAllType(selectBean));
                }
                SelectDialog.this.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
