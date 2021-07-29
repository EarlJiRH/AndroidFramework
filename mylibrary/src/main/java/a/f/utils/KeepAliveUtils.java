package a.f.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:22
 * 描述：进程保活 工具类（用于后台服务）
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class KeepAliveUtils {

    private PendingIntent pendingIntent;

    /** 开始保活 【requestCode = 请求码】【frequency = 复活频率，单位毫秒】 */
    public synchronized void startKeepAlive(Service service, int requestCode, long frequency) {
        startKeepAlive(service, requestCode, frequency, true);
    }

    /** 开始保活 【requestCode = 请求码】【frequency = 复活频率，单位毫秒】【isInexact = 是否不精确的循环频率】 */
    public synchronized void startKeepAlive(Service service, int requestCode, long frequency, boolean isInexact) {
        try {
            pendingIntent = PendingIntent.getService(service, requestCode, new Intent(service, service.getClass()).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK), PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) service.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                if (isInexact) {
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + frequency, frequency, pendingIntent);
                } else {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + frequency, frequency, pendingIntent);
                }
            }
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

    /** 停止保活 */
    public synchronized void stopKeepAlive(Service service) {
        try {
            if (pendingIntent != null) {
                AlarmManager alarmManager = (AlarmManager) service.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null)
                    alarmManager.cancel(pendingIntent);
                pendingIntent = null;
            }
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }
}
