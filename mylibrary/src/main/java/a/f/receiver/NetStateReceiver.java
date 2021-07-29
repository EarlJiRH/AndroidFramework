package a.f.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.simple.eventbus.EventBus;

import a.f.utils.HardwareUtils;
import a.f.utils.constant.AFEventBusTags;

/**
 * ================================================
 * 类名：a.f.receiver
 * 时间：2021/7/20 16:43
 * 描述：网络状态 广播接收器
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class NetStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        EventBus.getDefault().post(HardwareUtils.getInstance().getNetworkState(), AFEventBusTags.TAG_NET_STATE);

        //TODO log日志上传服务 暂时去除
//        try {
//            if (!HardwareUtils.getInstance().getNetworkState() || SystemUtils.checkServiceAlive(UploadLogService.class.getName())) {
//                return;
//            }
//            if (AppManager.getAppManager().getCurrentActivity() != null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                context.startService(new Intent(context, UploadLogService.class));
//            } else {// 兼容Android8.0启动服务方式
//                context.startForegroundService(new Intent(context, UploadLogService.class).putExtra(AFSF.KEY_IS_FOREGROUND_SERVICE, true));
//            }
//        } catch (Exception e) {
//            L.writeExceptionLog(e);
//        }
    }
}
