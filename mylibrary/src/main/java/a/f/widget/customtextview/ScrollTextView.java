package a.f.widget.customtextview;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * ================================================
 * 类名：a.f.widget.customtextview
 * 时间：2021/7/20 18:06
 * 描述：自定义 竖向滚动 控件
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ScrollTextView extends AppCompatTextView {

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
