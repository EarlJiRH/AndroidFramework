package a.f.widget.customtextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import a.f.utils.NumberUtils;

/**
 * ================================================
 * 类名：a.f.widget.customtextview
 * 时间：2021/7/20 18:06
 * 描述：数字变动 TextView
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class VariableTextView extends AppCompatTextView {

    private final long intervalTime = 16;
    private Thread mThread; // 执行数字变换的线程
    private Handler mHandler; // 主线程Handler
    private VariableRunnable mVariableRunnable; // 延时执行的任务
    private double mNumberCurrent; // 当前数字
    private long mTotalCount; // 变换总次数
    private String mFormat; // 数字格式
    private String mTextHead; // 头部文字
    private String mTextEnd; // 尾部文字

    public VariableTextView(Context context) {
        super(context);
    }

    public VariableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VariableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据
     *
     * @param numberCurrent 当前数字
     * @param totalTime     变换总时间 （单位 毫秒）
     * @param format        数字格式
     * @param textHead      前缀文本
     * @param textEnd       后缀文本
     */
    public void initVariable(double numberCurrent, long totalTime, String format, String textHead, String textEnd) {
        setAllCaps(false);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mNumberCurrent = numberCurrent;
        this.mTotalCount = totalTime / intervalTime;
        this.mFormat = TextUtils.isEmpty(format) ? "" : format;
        this.mTextHead = TextUtils.isEmpty(textHead) ? "" : textHead;
        this.mTextEnd = TextUtils.isEmpty(textEnd) ? "" : textEnd;
    }

    /** 开始变换数字，在原来数字基础上增加或减少，无需则传0 */
    public void startVariableForAddAndSub(double add, double sub) {
        startVariable(mNumberCurrent + add - sub);
    }

    /** 开始变换数字 */
    public void startVariable(double newNumber) {
        if (stopVariable()) {
            mHandler.postDelayed(mVariableRunnable = new VariableRunnable(newNumber), intervalTime);
        } else {
            executeVariable(newNumber);
        }
    }

    /** 延时执行的任务 */
    private class VariableRunnable implements Runnable {
        private double newNumber;

        private VariableRunnable(double newNumber) {
            this.newNumber = newNumber;
        }

        @Override
        public void run() {
            executeVariable(newNumber);
        }
    }

    /** 执行变换数字 */
    @SuppressLint("SetTextI18n")
    private void executeVariable(double newNumber) {
        mThread = new Thread(() -> {
            double mNumberCurrentOriginal = mNumberCurrent;
            double numID = Math.abs(mNumberCurrent - newNumber) / mTotalCount; // 递增递减值

            for (int i = 0; i < mTotalCount; i++) {
                if (!Thread.currentThread().isInterrupted()) {
                    if (mNumberCurrentOriginal > newNumber) {
                        mNumberCurrent -= numID;
                        if (mNumberCurrent <= newNumber) {
                            break;
                        }
                    } else if (mNumberCurrentOriginal < newNumber) {
                        mNumberCurrent += numID;
                        if (mNumberCurrent >= newNumber) {
                            break;
                        }
                    } else {
                        break;
                    }
                    post(() -> setText(mTextHead + NumberUtils.numberFormat(mFormat, mNumberCurrent) + mTextEnd));
                    SystemClock.sleep(intervalTime);
                } else {
                    break;
                }
            }
            mNumberCurrent = newNumber;
            post(() -> setText(mTextHead + NumberUtils.numberFormat(mFormat, mNumberCurrent) + mTextEnd));
            stopVariable();
        });
        mThread.start();
    }

    /** 停止变换数字 */
    private boolean stopVariable() {
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
            return true;
        }
        return false;
    }

    /** 界面销毁时调用 */
    public void onDestroy() {
        mHandler.removeCallbacks(mVariableRunnable);
        mHandler = null;
        mVariableRunnable = null;
        stopVariable();
    }

}
