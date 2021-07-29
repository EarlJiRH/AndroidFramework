package a.f.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

import com.jess.arms.integration.AppManager;

import java.lang.ref.WeakReference;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:26
 * 描述：屏幕尺寸 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DisplayMetricsUtils extends DisplayMetrics {

    private static volatile DisplayMetricsUtils displayMetricsUtils; // 本类实例
    private WeakReference<Activity> weakReference;
    private float refreshRate;

    private DisplayMetricsUtils(Activity activity) {
        super();
        setActivityRecord(activity);
    }

    private Activity getActivityRecord() {
        return weakReference.get();
    }

    private void setActivityRecord(Activity activity) {
        this.weakReference = new WeakReference<>(activity);
        Display d = activity.getWindowManager().getDefaultDisplay();
        d.getMetrics(this);
        refreshRate = d.getRefreshRate();
    }

    public float getRefreshRateInterior() {
        return refreshRate;
    }

    // =============================================================================================

    /** 获取屏幕宽度 【dividend = 需要 几分之1 的 屏幕宽度】【参数小于等于0 = 原始宽度】 */
    public int getScreenWidthDiv(double dividend) {
        return getScreenWidth(dividend, 1);
    }

    /** 获取屏幕宽度 【multiplicand = 需要 几倍的 屏幕宽度】【参数小于等于0 = 原始宽度】 */
    public int getScreenWidthMul(double multiplicand) {
        return getScreenWidth(1, multiplicand);
    }

    /** 获取屏幕宽度 【dividend = 需要 几分之1 的 屏幕宽度】【multiplicand = 需要 几倍的 屏幕宽度】【参数小于等于0 = 原始宽度】 */
    public int getScreenWidth(double dividend, double multiplicand) {
        if (dividend <= 0 || multiplicand <= 0) {
            return this.widthPixels;
        } else {
            return (int) (this.widthPixels / dividend * multiplicand);
        }
    }
    // =============================================================================================


    // =============================================================================================

    /** 获取屏幕高度 【dividend = 需要 几分之1 的 屏幕高度】【参数小于等于0 = 原始高度】 */
    public int getScreenHeightDiv(double dividend) {
        return getScreenHeight(dividend, 1);
    }

    /** 获取屏幕高度 【dividend = 需要 几倍的 屏幕高度】【参数小于等于0 = 原始高度】 */
    public int getScreenHeightMul(double multiplicand) {
        return getScreenHeight(1, multiplicand);
    }

    /** 获取屏幕高度 【dividend = 需要 几分之1 的 屏幕高度】【dividend = 需要 几倍的 屏幕高度】【参数小于等于0 = 原始高度】 */
    public int getScreenHeight(double dividend, double multiplicand) {
        if (dividend <= 0 || multiplicand <= 0) {
            return this.heightPixels;
        } else {
            return (int) (this.heightPixels / dividend * multiplicand);
        }
    }
    // =============================================================================================


    /** 获取 DisplayMetricsUtils 的实例 */
    public static DisplayMetricsUtils getInstance(Activity activity) {
        if (displayMetricsUtils == null) {
            synchronized (DisplayMetricsUtils.class) {
                if (displayMetricsUtils == null) {
                    displayMetricsUtils = new DisplayMetricsUtils(activity);
                }
            }
        } else if (displayMetricsUtils.getActivityRecord() != activity) {
            displayMetricsUtils.setActivityRecord(activity);
        }
        return displayMetricsUtils;
    }

    /** 获取刷新率 */
    public static float getRefreshRate() {
        Activity activity = AppManager.getAppManager().getTopActivity();
        if (activity == null) {
            return 0;
        }
        return getInstance(activity).getRefreshRateInterior();
    }
}
