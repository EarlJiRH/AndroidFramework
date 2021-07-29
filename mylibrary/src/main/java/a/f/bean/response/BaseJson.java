package a.f.bean.response;

import a.f.base.BaseBean;

/**
 * ================================================
 * 类名：a.f.bean.response
 * 时间：2021/7/20 16:42
 * 描述：基础响应数据模型 如果你服务器返回的数据格式固定为这种方式(这里只提供思想,服务器返回的数据格式可能不一致,可根据自家服务器返回的格式作更改)
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class BaseJson<T> extends BaseBean {
    /**
     * 默认成功码，可在程序运行时初始化该值
     */
    public static int SUCCESS_CODE = 0;

    protected int code;
    protected String msg;
    protected T data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    /**
     * 自定义成功码
     */
    public boolean isSuccess(int successCode) {
        return code == successCode;
    }
}
