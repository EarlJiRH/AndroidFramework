package a.f.base.impl;

import android.content.Context;
import android.net.ParseException;
import android.text.TextUtils;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import a.f.bean.response.BaseJson;
import a.f.utils.GsonUtils;
import a.f.utils.ToastUtils;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * ================================================
 * 类名：a.f.base.impl
 * 时间：2021/7/20 16:29
 * 描述：响应错误监听Impl
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ResponseErrorListenerImpl implements ResponseErrorListener {

    @Override
    public void handleResponseError(Context context, Throwable t) {
        //这里不光是只能打印错误,还可以根据不同的错误作出不同的逻辑处理
        String msg = "未知错误";
        if (t instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (t instanceof ConnectException) {
            msg = "请检查网络是否开启";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            ResponseBody errorBody = httpException.response().errorBody();
            try {
                if (errorBody != null) {
                    String errorJson = errorBody.string();
                    BaseJson baseJson = GsonUtils.getInstance().fromJson(errorJson, BaseJson.class);
                    msg = baseJson.getMsg();
                    if (TextUtils.isEmpty(msg)) {
                        msg = parseHttpException(httpException);
                    }
                } else {
                    msg = parseHttpException(httpException);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                msg = parseHttpException(httpException);
            }
        } else if (t instanceof JsonParseException || t instanceof ParseException || t instanceof JSONException) {
            msg = "数据解析错误";
        }
        ToastUtils.getInstance().showToast(msg);
    }

    /**
     * 解析 HttpException
     */
    public String parseHttpException(HttpException httpException) {
        String msg;
        switch (httpException.code()) {
            case 500:
                msg = "服务器发生错误";
                break;
            case 404:
                msg = "请求地址不存在";
                break;
            case 403:
                msg = "请求被服务器拒绝";
                break;
            case 307:
                msg = "请求被重定向到其他页面";
                break;
            default:
                msg = httpException.message();
                break;
        }
        return msg;
    }

}
