package a.f.utils.callback;

import java.util.List;

/**
 * ================================================
 * 类名：a.f.utils.callback
 * 时间：2021/7/20 17:39
 * 描述：回调工具类 带参数
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public interface CallBackValue {
    /** 成功回调 带参数 */
    void onBack(List<Object> values);

    /** 错误回调 带参数 */
    default void onError(List<Object> values) {
    }

    /** 取消回调 带参数 */
    default void onCancel(List<Object> values) {
    }

    /** 完成回调 带参数 */
    default void onComplete(List<Object> values) {
    }
}
