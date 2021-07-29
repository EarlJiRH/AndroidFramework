package a.f.utils.callback;

/**
 * ================================================
 * 类名：a.f.utils.callback
 * 时间：2021/7/20 17:39
 * 描述：回调工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public interface CallBack {

    /** 成功回调 */
    void onBack();

    /** 错误回调 */
    default void onError() {
    }

    /** 取消回调 */
    default void onCancel() {
    }

    /** 完成回调 */
    default void onComplete() {
    }
}
