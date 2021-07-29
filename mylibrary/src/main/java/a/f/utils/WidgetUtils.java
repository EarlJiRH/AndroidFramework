package a.f.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jess.arms.utils.ArmsUtils;

import org.jetbrains.annotations.NotNull;

import a.f.base.BaseApp;
import a.f.utils.callback.CallBackValue;
import a.f.utils.constant.AFConfig;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:32
 * 描述：控件 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class WidgetUtils {


    /** 启动一个 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivity(Fragment fragment, Class<?> cls, boolean isFinishCurrent) {
        startActivity(fragment, new Intent(fragment.getActivity(), cls), isFinishCurrent);
    }

    /** 启动一个 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivity(Fragment fragment, Intent intent, boolean isFinishCurrent) {
        startActivity(fragment.getActivity(), intent, isFinishCurrent);
    }

    /** 启动一个 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivity(Activity activity, Class<?> cls, boolean isFinishCurrent) {
        startActivity(activity, new Intent(activity, cls), isFinishCurrent);
    }

    /** 启动一个 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivity(Activity activity, Intent intent, boolean isFinishCurrent) {
        try {
            activity.startActivity(intent);
            if (isFinishCurrent) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 启动一个有回调 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivityForResult(Fragment fragment, Class<?> cls, int requestCode, boolean isFinishCurrent) {
        startActivityForResult(fragment, new Intent(fragment.getActivity(), cls), requestCode, isFinishCurrent);
    }

    /** 启动一个有回调 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivityForResult(Fragment fragment, Intent intent, int requestCode, boolean isFinishCurrent) {
        startActivityForResult(fragment.getActivity(), intent, requestCode, isFinishCurrent);
    }

    /** 启动一个有回调 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivityForResult(Activity activity, Class<?> cls, int requestCode, boolean isFinishCurrent) {
        startActivityForResult(activity, new Intent(activity, cls), requestCode, isFinishCurrent);
    }

    /** 启动一个有回调 Activity  【isFinishCurrent = 是否关闭当前界面】 */
    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, boolean isFinishCurrent) {
        try {
            activity.startActivityForResult(intent, requestCode);
            if (isFinishCurrent) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 启动一个 新栈 Activity 通过上下文 【已 addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)】 */
    public static void startActivity(Intent intent) {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ArmsUtils.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 通过Tag找到缓存的Fragment */
    public static Fragment findFragmentByTag(FragmentManager fragmentManager, String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    /** 显示 Fragment （适用于第一次显示）【resId = 父容器ID】 */
    public static Fragment showFragment(FragmentActivity fragmentActivity, Fragment fragment, int resId) {
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment).commitAllowingStateLoss();
        } else {
            fragmentTransaction.add(resId, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        }
        return fragment;
    }

    /**
     * 切换 Fragment 【show = 需显示的Fragment】【hide = 需切出的Fragment】【resId = 父容器ID】
     * 返回： 显示中的Fragment
     * 例子： fragmentShowing = WidgetUtils.changeFragment(this, fragmentShow, fragmentHide, R.id.fragmentRoot);
     */
    public static Fragment changeFragment(FragmentActivity fragmentActivity, Fragment show, Fragment hide, int resId) {
        return changeFragment(fragmentActivity, show, hide, resId, true);
    }

    /**
     * 切换 Fragment 【show = 需显示的Fragment】【hide = 需切出的Fragment】【resId = 父容器ID】【isAnim = 是否需要动画】
     * 返回： 显示中的Fragment
     */
    public static Fragment changeFragment(FragmentActivity fragmentActivity, Fragment show, Fragment hide, int resId, boolean isAnim) {
        return changeFragment(fragmentActivity, show, hide, resId, isAnim, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 切换 Fragment 【show = 需显示的Fragment】【hide = 需切出的Fragment】【resId = 父容器ID】【animResIdIn = 显示动画】【animResIdOut = 隐藏动画】
     * 返回： 显示中的Fragment
     */
    public static Fragment changeFragment(FragmentActivity fragmentActivity, Fragment show, Fragment hide, int resId, int animResIdIn, int animResIdOut) {
        return changeFragment(fragmentActivity, show, hide, resId, true, animResIdIn, animResIdOut);
    }

    /**
     * 切换 Fragment 【show = 需显示的Fragment】【hide = 需切出的Fragment】【resId = 父容器ID】【isAnim = 是否需要动画】【animResIdIn = 显示动画】【animResIdOut = 隐藏动画】
     * 返回： 显示中的Fragment
     */
    public static Fragment changeFragment(FragmentActivity fragmentActivity, Fragment show, Fragment hide, int resId, boolean isAnim, int animResIdIn, int animResIdOut) {
        if (show == hide) {
            return show;
        }

        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (isAnim) {
            fragmentTransaction.setCustomAnimations(animResIdIn, animResIdOut);
        }

        if (hide != null) {
            fragmentTransaction.hide(hide);
        }
        if (show.isAdded()) {
            fragmentTransaction.show(show).commitAllowingStateLoss();
        } else {
            fragmentTransaction.add(resId, show, show.getClass().getName()).commitAllowingStateLoss();
        }
        return show;
    }

    /** 通过包名 启动一个 App */
    public static void startApp(String appPackageName) {
        try {
            Intent intent = BaseApp.getI().getPackageManager().getLaunchIntentForPackage(appPackageName);
            ArmsUtils.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast("失败，没有安装：" + appPackageName);
        }
    }

    /** 通过包名&类名 启动一个 App */
    public static void startApp(String appPackageName, String className) {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(appPackageName, className);
            intent.setComponent(componentName);
            ArmsUtils.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.getInstance().showToast("失败，没有安装：" + appPackageName + " - " + className);
        }
    }

    /**
     * 弹出选择日期的框 月份需从1开始
     *
     * @param callBackValue 回调，根据下标获取对应数据【0 = 年】【1 = 月】【2 = 日】【3 = 字符串年月日】【4 = 时间戳】
     * @param isCancelable  当用户点击返回按钮时是否能关闭弹窗
     * @param year          年
     * @param monthOfYear   月
     * @param dayOfMonth    日
     */
    public static void showDatePickerDialog(Activity activity, @NotNull CallBackValue callBackValue, boolean isCancelable, int year, int monthOfYear, int dayOfMonth) {
        showDatePickerDialog(activity, callBackValue, isCancelable, year, monthOfYear, dayOfMonth, Long.MIN_VALUE, Long.MIN_VALUE);
    }

    /**
     * 弹出选择日期的框 月份需从1开始
     *
     * @param callBackValue 回调，根据下标获取对应数据【0 = 年】【1 = 月】【2 = 日】【3 = 字符串年月日】【4 = 时间戳】
     * @param isCancelable  当用户点击返回按钮时是否能关闭弹窗
     * @param year          年
     * @param monthOfYear   月
     * @param dayOfMonth    日
     * @param minDate       能选择最小时间
     * @param maxDate       能选择最大时间
     */
    public static void showDatePickerDialog(Activity activity, @NotNull CallBackValue callBackValue, boolean isCancelable, int year, int monthOfYear, int dayOfMonth, long minDate, long maxDate) {
        try {
            if (year < 1900) {
                year = Integer.parseInt(DateTimeUtils.getNowTime(AFSF.DT_009));
            }
            if (monthOfYear < 1) {
                monthOfYear = Integer.parseInt(DateTimeUtils.getNowTime(AFSF.DT_010));
            }
            if (dayOfMonth < 1) {
                dayOfMonth = Integer.parseInt(DateTimeUtils.getNowTime(AFSF.DT_011));
            }
            DatePickerDialog datePickerDialog = new DatePickerDialog(activity, (view, yearNew, monthOfYearNew, dayOfMonthNew) -> callBackValue.onBack(AFVariableUtils.getListForAllType(
                    yearNew,
                    monthOfYearNew + 1,
                    dayOfMonthNew,
                    DateTimeUtils.getTimesToDate(AFSF.DT_003, yearNew, monthOfYearNew + 1, dayOfMonthNew),
                    DateTimeUtils.getTimesToTimestamp(yearNew, monthOfYearNew + 1, dayOfMonthNew)
            )), year, (monthOfYear - 1), dayOfMonth);

            DatePicker datePicker = datePickerDialog.getDatePicker();
            if (minDate != Long.MIN_VALUE) {
                datePicker.setMinDate(minDate);
            }
            if (maxDate != Long.MIN_VALUE) {
                datePicker.setMaxDate(maxDate);
            }

            setDialogGravity(datePickerDialog);
            datePickerDialog.setCancelable(isCancelable);
            datePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 弹出选择时间的框
     *
     * @param callBack     回调
     * @param isCancelable 当用户点击返回按钮时是否能关闭弹窗
     * @param hourOfDay    时
     * @param minute       分
     * @param is24HourView 是否24小时制
     */
    public static void showTimePickerDialog(Activity activity, @NotNull TimePickerDialog.OnTimeSetListener callBack, boolean isCancelable, int hourOfDay, int minute, boolean is24HourView) {
        try {
            TimePickerDialog timePickerDialog = new TimePickerDialog(activity, callBack, hourOfDay, minute, is24HourView);
            setDialogGravity(timePickerDialog);
            timePickerDialog.setCancelable(isCancelable);
            timePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 选择年月日时分
     *
     * @param callBackValue 回调，根据下标获取对应数据【0 = 年】【1 = 月】【2 = 日】【3 = 时】【4 = 分】【5 = 时间戳】
     * @param isCancelable  当用户点击返回按钮时是否能关闭弹窗
     * @param year          年
     * @param monthOfYear   月
     * @param dayOfMonth    日
     * @param hourOfDay     时
     * @param minute        分
     */
    public static void selectDateTime(Activity activity, @NotNull CallBackValue callBackValue, boolean isCancelable, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
        selectDateTime(activity, callBackValue, isCancelable, year, monthOfYear, dayOfMonth, hourOfDay, minute, Long.MIN_VALUE, Long.MIN_VALUE);
    }

    /**
     * 选择年月日时分
     *
     * @param callBackValue 回调，根据下标获取对应数据【0 = 年】【1 = 月】【2 = 日】【3 = 时】【4 = 分】【5 = 时间戳】
     * @param isCancelable  当用户点击返回按钮时是否能关闭弹窗
     * @param year          年
     * @param monthOfYear   月
     * @param dayOfMonth    日
     * @param hourOfDay     时
     * @param minute        分
     * @param minDate       能选择最小时间
     * @param maxDate       能选择最大时间
     */
    public static void selectDateTime(Activity activity, @NotNull CallBackValue callBackValue, boolean isCancelable, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, long minDate, long maxDate) {
        showDatePickerDialog(activity, values -> {
            int newY = (int) values.get(0);
            int newM = (int) values.get(1);
            int newD = (int) values.get(2);
            showTimePickerDialog(activity, (view, hourOfDayNew, minuteNew) -> callBackValue.onBack(AFVariableUtils.getListForAllType(newY, newM, newD, hourOfDayNew, minuteNew, DateTimeUtils.getTimesToTimestamp(newY, newM, newD, hourOfDayNew, minuteNew))), isCancelable, hourOfDay, minute, true);
        }, isCancelable, year, monthOfYear, dayOfMonth, minDate, maxDate);
    }

    /** 创建空的通知，适用于启动前台Service */
    public static Notification createNullNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String appName = AFConfig.APP_NAME;
            NotificationManager notificationManager = (NotificationManager) BaseApp.getI().getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(appName);
            if (notificationChannel == null) {
                notificationChannel = new NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_LOW);
                notificationManager.createNotificationChannel(notificationChannel);
            }
            return new Notification.Builder(BaseApp.getI(), appName).build();
        } else {
            return null;
        }
    }

    /** 本地推送消息 */
    public static void showNotification(int id, int iconRId, String title, String content, String info, String tickerText, int number, boolean isOngoing, PendingIntent pendingIntent, Intent intent) {
        try {
            NotificationManager notificationManager = (NotificationManager) BaseApp.getI().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) {
                return;
            }

            String appName = AFConfig.APP_NAME;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = notificationManager.getNotificationChannel(appName);
                if (notificationChannel == null) {
                    notificationChannel = new NotificationChannel(appName, appName, NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setDescription(appName); // 描述
                    notificationChannel.enableLights(true); // 设置通知出现时的闪灯
                    notificationChannel.setLightColor(Color.RED); // 灯光颜色
                    notificationChannel.enableVibration(true); // 设置通知出现时的震动
                    notificationChannel.setVibrationPattern(new long[]{0, 200, 100, 800}); // 震动模式
                    notificationManager.createNotificationChannel(notificationChannel); // 创建通知
                }
            }

            NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(BaseApp.getI(), appName);
            ncBuilder.setContentTitle(title); // 设置通知栏标题
            ncBuilder.setContentText(content); // 设置通知栏显示内容
            ncBuilder.setContentInfo(info); // 附加消息
            ncBuilder.setTicker(tickerText); // 通知首次出现在通知栏，带上升动画效果的
            ncBuilder.setNumber(number); // 设置通知集合的数量
            ncBuilder.setWhen(System.currentTimeMillis()); // 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
            ncBuilder.setAutoCancel(true); // 设置这个标志当用户单击面板就可以让通知将自动取消
            ncBuilder.setOngoing(isOngoing); // true，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
            ncBuilder.setDefaults(Notification.DEFAULT_ALL); // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合 //Notification.DEFAULT_ALLNotification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
            ncBuilder.setSmallIcon(iconRId); // 设置通知小ICON
            ncBuilder.setPriority(Notification.PRIORITY_MAX); // 设置该通知优先级
            if (pendingIntent != null) {
                ncBuilder.setContentIntent(pendingIntent);
            } else if (intent != null) {
                ncBuilder.setContentIntent(PendingIntent.getActivity(BaseApp.getI(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT)); // 设置通知栏点击意图
            } else {
                ncBuilder.setContentIntent(PendingIntent.getActivity(BaseApp.getI(), id, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT)); // 设置通知栏点击意图
            }
            Notification notification = ncBuilder.build();
            notificationManager.notify(id, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 清空全部通知 */
    public static void clearNotification() {
        clearNotification(-1);
    }

    /** 清除指定通知 */
    public static void clearNotification(int id) {
        try {
            NotificationManager notificationManager = (NotificationManager) BaseApp.getI().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager == null) {
                return;
            }

            if (id == -1) {
                notificationManager.cancelAll();
            } else {
                notificationManager.cancel(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 收起/关闭 虚拟键盘/输入法 */
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return;
            }

            View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 收起/关闭 虚拟键盘/输入法 适用于View类型 */
    public static void hideKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 打开/显示 虚拟键盘/输入法 */
    public static void openKeyboard(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return;
            }

            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            // inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS); // 用法同上
            // inputMethodManager.showSoftInput(view, InputMethodManager.RESULT_SHOWN); // 用法同上
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 显示 明显 错误提示 */
    public static void showErrorHint(TextView textView, int errorHintResourceId) {
        showErrorHint(textView, BaseApp.getI().getString(errorHintResourceId));
    }

    /** 显示 明显 错误提示 */
    public static void showErrorHint(TextView textView, String errorHint) {
        textView.setError(errorHint);
        textView.requestFocus();
        ToastUtils.getInstance().showToast(errorHint);
    }

    /** 快捷关闭弹窗 */
    public static void rapidDismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /** 设置弹窗位置（默认居中，适用于适配 AndroidX 的弹窗，AndroidX 的弹窗在低版本系统默认显示在左上角） */
    public static void setDialogGravity(Dialog dialog) {
        setDialogGravity(dialog, Gravity.CENTER);
    }

    /** 设置弹窗位置 */
    public static void setDialogGravity(Dialog dialog, int gravity) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(gravity);
        }
    }
}
