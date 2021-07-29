package a.f.widget.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

import a.f.R;
import a.f.utils.SystemUtils;

/**
 * ================================================
 * 类名：a.f.widget.customtextview
 * 时间：2021/7/20 15:28
 * 描述：有删除和隐藏输入内容按钮的 输入框
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ButtonEditText extends AppCompatEditText {

    private Bitmap bitmapDelete; // 删除图标
    private int colorShow; // 显示文本图标 颜色（在隐藏文本图标上渲染颜色，优先使用这个参数，如果为null，则使用显示文本图标）
    private Bitmap bitmapShow; // 显示文本图标
    private Bitmap bitmapHidden; // 隐藏文本图标
    private boolean isEnabledShow; // 是否启用眼睛
    private boolean isShow; // 是否显示文本
    private boolean isTextInput; // 是否是文本输入类型，否则是纯数字（适用于纯数字支付密码）
    private int imageDMargin; // 删除图片边距
    private int imageSHMargin; // 显示隐藏图片边距
    private int leftDelete, leftShow, leftHidden, topDelete, topShow, topHidden; // 删除图标左边距，显示文本图标左边距，隐藏文本图标左边距，删除图标上边距，显示文本图标上边距，隐藏文本图标上边距
    private Paint paintColorShow; // 颜色画笔

    public ButtonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ButtonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ButtonEditText);
        isEnabledShow = typedArray.getBoolean(R.styleable.ButtonEditText_isEnabledShow, false);
        isShow = typedArray.getBoolean(R.styleable.ButtonEditText_isShow, !isEnabledShow);
        isTextInput = typedArray.getBoolean(R.styleable.ButtonEditText_isTextInput, true);
        bitmapDelete = drawableToBitmap(typedArray.getDrawable(R.styleable.ButtonEditText_bitmapDelete));
        colorShow = typedArray.getColor(R.styleable.ButtonEditText_colorShow, 0);
        bitmapShow = drawableToBitmap(typedArray.getDrawable(R.styleable.ButtonEditText_bitmapShow));
        bitmapHidden = drawableToBitmap(typedArray.getDrawable(R.styleable.ButtonEditText_bitmapHidden));
        imageDMargin = typedArray.getDimensionPixelSize(R.styleable.ButtonEditText_imageDMargin, SystemUtils.dpToPx(10));
        imageSHMargin = typedArray.getDimensionPixelSize(R.styleable.ButtonEditText_imageSHMargin, SystemUtils.dpToPx(10));
        typedArray.recycle();

        if (colorShow < 0) {
            paintColorShow = new Paint();
            paintColorShow.setColorFilter(new PorterDuffColorFilter(colorShow, PorterDuff.Mode.SRC_IN));
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!isEnabled() || !isFocused() || TextUtils.isEmpty(getText())) {
            return;
        }

        int width = getMeasuredWidth() + getScrollX();
        int height = getMeasuredHeight();

        leftDelete = width - bitmapDelete.getWidth() - imageDMargin;
        topDelete = height / 2 - bitmapDelete.getHeight() / 2;
        canvas.drawBitmap(bitmapDelete, leftDelete, topDelete, null);

        if (!isEnabledShow) {
            return;
        }
        if (isShow && colorShow == 0) {
            leftShow = width - bitmapShow.getWidth() - bitmapDelete.getWidth() - imageSHMargin - imageDMargin;
            topShow = height / 2 - bitmapShow.getHeight() / 2;
            canvas.drawBitmap(bitmapShow, leftShow, topShow, null);
        } else {
            leftHidden = width - bitmapHidden.getWidth() - bitmapDelete.getWidth() - imageSHMargin - imageDMargin;
            topHidden = height / 2 - bitmapHidden.getHeight() / 2;
            canvas.drawBitmap(bitmapHidden, leftHidden, topHidden, isShow && colorShow < 0 ? paintColorShow : null);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && isEnabled() && !TextUtils.isEmpty(getText())) {
            float xTouch = event.getX();
            float yTouch = event.getY();
            int deleteL = leftDelete - getScrollX();
            int deleteR = leftDelete + bitmapDelete.getWidth() - getScrollX();
            int deleteT = topDelete;
            int deleteB = topDelete + bitmapDelete.getHeight();

            if (xTouch >= deleteL && xTouch <= deleteR && yTouch >= deleteT && yTouch <= deleteB) {
                // 删除文本
                setText(null);
            } else if (isEnabledShow) {
                int imageL;
                int imageR;
                int imageT;
                int imageB;

                if (isShow && colorShow == 0) {
                    imageL = leftShow - getScrollX();
                    imageR = leftShow + bitmapShow.getWidth() - getScrollX();
                    imageT = topShow;
                    imageB = topShow + bitmapShow.getHeight();
                } else {
                    imageL = leftHidden - getScrollX();
                    imageR = leftHidden + bitmapHidden.getWidth() - getScrollX();
                    imageT = topHidden;
                    imageB = topHidden + bitmapHidden.getHeight();
                }

                if (xTouch >= imageL && xTouch <= imageR && yTouch >= imageT && yTouch <= imageB) {
                    isShow = !isShow;
                    setIsTextInput(isTextInput);
                    Editable text = getText();
                    if (!TextUtils.isEmpty(text)) {
                        setSelection(text.length());
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * Drawable 转 Bitmap
     *
     * @param drawable Drawable对象
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /** 设置 是否是文本输入类型，否则是纯数字（适用于纯数字支付密码） */
    public void setIsTextInput(boolean isTextInput) {
        this.isTextInput = isTextInput;
        if (isTextInput) {
            setInputType(isShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            setInputType(isShow ? InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL : InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        }
    }

}
