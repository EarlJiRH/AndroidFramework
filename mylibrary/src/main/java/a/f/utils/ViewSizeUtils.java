package a.f.utils;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

import a.f.base.BaseApp;
import a.f.utils.callback.CallBack;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:32
 * 描述：控件尺寸变换 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ViewSizeUtils {


    /**
     * 动态改变控件宽或高
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【true = 横向|宽】【false = 竖线|高】
     * @param view        指定控件
     * @param callBack    完成动画回调
     */
    public static void changeViewWH(int minSize, int maxSize, boolean state, boolean orientation, View view, CallBack callBack) {
        changeViewWH(minSize, maxSize, state, orientation, view, VSParam.DEFAULT, callBack);
    }

    /**
     * 动态改变控件宽或高
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【true = 横向|宽】【false = 竖线|高】
     * @param view        指定控件
     * @param vsp         时间相关参数
     * @param callBack    完成动画回调
     */
    public static void changeViewWH(int minSize, int maxSize, boolean state, boolean orientation, View view, VSParam vsp, CallBack callBack) {
        ThreadPoolUtils.getInstance().submit(() -> {
            if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                ToastUtils.getInstance().showToast("不支持此类型控件");
                return;
            }

            float intervalNum = vsp.getCVSIT() / (vsp.getCVSTT() / (maxSize - minSize));
            if (state) { // 展开 minSize ~ maxSize
                for (int i = minSize; i < maxSize; i += intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewWH(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewWH(maxSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            } else { // 收起 maxSize ~ minSize
                for (int i = maxSize; i > minSize; i -= intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewWH(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewWH(minSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            }
        });
    }

    /**
     * 动态改变控件宽或高
     *
     * @param s    尺寸
     * @param o    方向【true = 横向|宽】【false = 竖线|高】
     * @param view 指定控件
     */
    private static void changeViewWH(int s, boolean o, View view) {
        BaseApp.getI().mHandler.post(() -> {
            if (view.getWindowToken() == null) {
                return;
            }
            ViewGroup.MarginLayoutParams l = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (o) {
                l.width = s;
            } else {
                l.height = s;
            }
            view.setLayoutParams(l);
        });
    }

    // =============================================================================================

    /**
     * 动态改变控件某一方向内边距
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view        指定控件
     * @param callBack    完成动画回调
     */
    public static void changeViewPadding(int minSize, int maxSize, boolean state, int orientation, View view, CallBack callBack) {
        changeViewPadding(minSize, maxSize, state, orientation, view, VSParam.DEFAULT, callBack);
    }

    /**
     * 动态改变控件某一方向内边距
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view        指定控件
     * @param vsp         时间相关参数
     * @param callBack    完成动画回调
     */
    public static void changeViewPadding(int minSize, int maxSize, boolean state, int orientation, View view, VSParam vsp, CallBack callBack) {
        ThreadPoolUtils.getInstance().submit(() -> {
            float intervalNum = vsp.getCVSIT() / (vsp.getCVSTT() / (maxSize - minSize));
            if (state) { // 展开 minSize ~ maxSize
                for (int i = minSize; i < maxSize; i += intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewPadding(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewPadding(maxSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            } else { // 收起 maxSize ~ minSize
                for (int i = maxSize; i > minSize; i -= intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewPadding(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewPadding(minSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            }
        });
    }

    /**
     * 动态改变控件某一方向内边距
     *
     * @param s    尺寸
     * @param o    方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view 指定控件
     */
    private static void changeViewPadding(int s, int o, View view) {
        BaseApp.getI().mHandler.post(() -> {
            if (view.getWindowToken() == null) {
                return;
            }
            view.setPadding(
                    o == 1 ? s : view.getPaddingLeft(),
                    o == 2 ? s : view.getPaddingTop(),
                    o == 3 ? s : view.getPaddingRight(),
                    o == 4 ? s : view.getPaddingBottom());
        });
    }

    // =============================================================================================

    /**
     * 动态改变控件某一方向外边距（View xml禁止配置layout_marginEnd和layout_marginStart属性）
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view        指定控件
     * @param callBack    完成动画回调
     */
    public static void changeViewMargin(int minSize, int maxSize, boolean state, int orientation, View view, CallBack callBack) {
        changeViewMargin(minSize, maxSize, state, orientation, view, VSParam.DEFAULT, callBack);
    }

    /**
     * 动态改变控件某一方向外边距（View xml禁止配置layout_marginEnd和layout_marginStart属性）
     *
     * @param minSize     控件最小尺寸
     * @param maxSize     控件最大尺寸
     * @param state       状态【true = 展开】【false = 收起】
     * @param orientation 方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view        指定控件
     * @param vsp         时间相关参数
     * @param callBack    完成动画回调
     */
    public static void changeViewMargin(int minSize, int maxSize, boolean state, int orientation, View view, VSParam vsp, CallBack callBack) {
        ThreadPoolUtils.getInstance().submit(() -> {
            if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                ToastUtils.getInstance().showToast("不支持此类型控件");
                return;
            }

            float intervalNum = vsp.getCVSIT() / (vsp.getCVSTT() / (maxSize - minSize));
            if (state) { // 展开 minSize ~ maxSize
                for (int i = minSize; i < maxSize; i += intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewMargin(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewMargin(maxSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            } else { // 收起 maxSize ~ minSize
                for (int i = maxSize; i > minSize; i -= intervalNum) {
                    if (view.getWindowToken() == null) {
                        return;
                    }
                    SystemClock.sleep(vsp.getCVSIT());
                    changeViewMargin(i, orientation, view);
                }
                if (view.getWindowToken() == null) {
                    return;
                }
                SystemClock.sleep(vsp.getCVSIT());
                changeViewMargin(minSize, orientation, view);

                BaseApp.getI().mHandler.post(() -> {
                    if (view.getWindowToken() != null && callBack != null) {
                        callBack.onBack();
                    }
                });
            }
        });
    }

    /**
     * 动态改变控件某一方向外边距
     *
     * @param s    尺寸
     * @param o    方向【1 = 左】【2 = 上】【3 = 右】【4 = 下】
     * @param view 指定控件
     */
    private static void changeViewMargin(int s, int o, View view) {
        BaseApp.getI().mHandler.post(() -> {
            if (view.getWindowToken() == null) {
                return;
            }
            ViewGroup.MarginLayoutParams l = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            l.setMargins(
                    o == 1 ? s : l.leftMargin,
                    o == 2 ? s : l.topMargin,
                    o == 3 ? s : l.rightMargin,
                    o == 4 ? s : l.bottomMargin);
            view.setLayoutParams(l);
        });
    }

    // =============================================================================================

    /** 控件尺寸变换 参数类 */
    public static class VSParam {
        private float mChangeViewSizeTotalTime; // 改变控件尺寸 总耗时
        private long mChangeViewSizeIntervalTime; // 改变控件尺寸 间隔时间

        private VSParam() {
            this(200); // 默认200
        }

        private VSParam(float changeViewSizeTotalTime) {
            if (changeViewSizeTotalTime < 1) {
                throw new IllegalArgumentException("参数不能小于1");
            }
            mChangeViewSizeTotalTime = changeViewSizeTotalTime;
            mChangeViewSizeIntervalTime = (long) (1000f / DisplayMetricsUtils.getRefreshRate());
        }

        private float getCVSTT() {
            return mChangeViewSizeTotalTime;
        }

        private long getCVSIT() {
            return mChangeViewSizeIntervalTime;
        }

        /** 默认配置总耗时 */
        public static final VSParam DEFAULT = new VSParam();

        /**
         * 配置总耗时参数
         *
         * @param changeViewSizeTotalTime 改变控件尺寸 总耗时
         */
        public static VSParam configTotalTime(float changeViewSizeTotalTime) {
            return new VSParam(changeViewSizeTotalTime);
        }
    }
}
