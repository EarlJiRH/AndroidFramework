package a.f.utils;

import com.jess.arms.utils.ArmsUtils;

import a.f.R;
import a.f.utils.constant.AFConfig;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:25
 * 描述：双击退出 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DoubleClickExitUtils {

    public static long TIME_DBLCLICK = AFSF.TIME_1S; // 双击退出 App 间隔时间 毫秒；默认1秒，可在使用之前更改
    private static long mDblclickTime; // 双击退出时间记录

    /** 双击退出应用程序 */
    public static void appDblclickExit() {
        try {
            if (DateTimeUtils.getNowTime() - mDblclickTime < TIME_DBLCLICK) {
                ArmsUtils.exitApp();
            } else {
                mDblclickTime = DateTimeUtils.getNowTime();
                ToastUtils.getInstance().showToast(false, R.string.hintDoubleClickExit, AFConfig.APP_NAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
