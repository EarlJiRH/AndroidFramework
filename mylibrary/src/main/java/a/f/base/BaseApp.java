package a.f.base;

import android.content.IntentFilter;
import android.os.Handler;

import com.jess.arms.base.BaseApplication;

import a.f.receiver.NetStateReceiver;
import a.f.utils.UnCapturedExceptionRecord;

/**
 * ================================================
 * 类名：a.f.base
 * 时间：2021/7/20 15:32
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class BaseApp extends BaseApplication {

    private static BaseApp mBaseApp;
    public Handler mHandler = new Handler();

    public BaseApp() {
        mBaseApp = this;
    }

    public static BaseApp getI() {
        return mBaseApp;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: a.f.base.BaseApp */
    /* JADX WARN: Multi-variable type inference failed */
    @Override
    public void onCreate() {
        BaseApp.super.onCreate();
        UnCapturedExceptionRecord.getInstance().init();
//        QbSdk.disableSensitiveApi();
//        Map<String, Object> tbsMap = new HashMap<>();
//        tbsMap.put("use_speedy_classloader", true);
//        tbsMap.put("use_dexloader_service", true);
//        QbSdk.initTbsSettings(tbsMap);
//        QbSdk.initX5Environment(this, null);
        registerReceiver(new NetStateReceiver(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }


}
