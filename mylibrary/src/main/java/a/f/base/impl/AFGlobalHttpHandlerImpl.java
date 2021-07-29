package a.f.base.impl;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.jess.arms.http.GlobalHttpHandler;

import java.lang.reflect.Field;

import a.f.R;
import a.f.base.BaseApp;
import a.f.utils.L;
import a.f.utils.constant.AFConfig;
import okhttp3.Interceptor;
import okhttp3.internal.http.RealInterceptorChain;

/**
 * ================================================
 * 类名：a.f.base.impl
 * 时间：2021/7/20 16:28
 * 描述：全局 HTTP 处理程序 impl
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public abstract class AFGlobalHttpHandlerImpl implements GlobalHttpHandler {

    protected Context mContext;

    protected AFGlobalHttpHandlerImpl(Context context) {
        this.mContext = context;
    }

    /** 设置超时时间 */
    protected void setTimeout(Interceptor.Chain chain, long timeout) {
        try {
            RealInterceptorChain ric = (RealInterceptorChain) chain;
            Class<?> classType = ric.getClass();
            Field connectTimeout = classType.getDeclaredField("connectTimeoutMillis");
            Field readTimeout = classType.getDeclaredField("readTimeoutMillis");
            Field writeTimeout = classType.getDeclaredField("writeTimeoutMillis");
            connectTimeout.setAccessible(true);
            readTimeout.setAccessible(true);
            writeTimeout.setAccessible(true);
            // 为了兼容okhttp4.0需要强转为int类型
            connectTimeout.set(ric, (int) timeout);
            readTimeout.set(ric, (int) timeout);
            writeTimeout.set(ric, (int) timeout);
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

    /**
     * 检查是否允许代理
     * <p>
     * 允许   设置了  ok
     * 允许   没设置  ok
     * 不允许 设置了  no
     * 不允许 没设置  ok
     */
    protected boolean isPass() {
        boolean isPass = true;
        try {
            if (!AFConfig.PERMIT_PROXY && !TextUtils.isEmpty(System.getProperty("http.proxyHost"))) {
                BaseApp.getI().mHandler.post(() -> Toast.makeText(mContext, R.string.hintNoProxy, Toast.LENGTH_LONG).show());
                isPass = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            isPass = false;
            L.writeExceptionLog(e);
        }
        return isPass;
    }
}
