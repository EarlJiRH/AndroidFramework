package a.f.widget.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

/**
 * ================================================
 * 类名：a.f.widget.customtextview
 * 时间：2021/7/20 18:07
 * 描述：自定义 自减数字 按钮 组件【可用于获取验证码的按钮】
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DecrementButton extends AppCompatButton {

    private Thread mThread;
    private boolean mIsDecrement; // 开关
    private long mNumberTotal; // 总数字
    private long mNumberTime; // 每次自减间隔时间 （单位 毫秒）
    private String mTextOriginal; // 文本 原始内容/倒计时完成时显示的文本
    private String mTextCountHead; // 文本倒计时 头部文字
    private String mTextCountEnd; // 文本倒计时 尾部文字

    public DecrementButton(Context context) {
        super(context);
        init();
    }

    public DecrementButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DecrementButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /** 初始化 */
    private void init() {
        setAllCaps(false);
    }

    /** 启动倒计时 【numberTotal = 总数】【numberTime = 每次间隔时间】【textOriginal = 文本内容】【textCountHead = 前缀】【textCountEnd = 后缀】 */
    @SuppressLint("SetTextI18n")
    public void startDecrement(long numberTotal, long numberTime, String textOriginal, String textCountHead, String textCountEnd) {
        if (mIsDecrement) {
            return;
        }

        this.mIsDecrement = true;
        this.mNumberTotal = numberTotal;
        this.mNumberTime = numberTime;
        this.mTextOriginal = TextUtils.isEmpty(textOriginal) ? "" : textOriginal;
        this.mTextCountHead = TextUtils.isEmpty(textCountHead) ? "" : textCountHead;
        this.mTextCountEnd = TextUtils.isEmpty(textCountEnd) ? "" : textCountEnd;
        setEnabled(false);

        mThread = new Thread(() -> {
            while (mIsDecrement && !Thread.currentThread().isInterrupted() && (mNumberTotal >= 0)) {
                post(() -> setText(mTextCountHead + mNumberTotal + mTextCountEnd)); // 倒计时进行中，改变文本
                SystemClock.sleep(mNumberTime);
                mNumberTotal--;
            }

            // 倒计时停止，恢复控件状态
            post(() -> {
                setEnabled(true);
                setText(mTextOriginal);
                stopDecrement();
            });
        });
        mThread.start();
    }

    /** 停止倒计时 */
    public void stopDecrement() {
        mIsDecrement = false;
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }
    }
}
