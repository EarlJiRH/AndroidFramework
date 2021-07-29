package a.f.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import a.f.widget.xrview.TYRecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:15
 * 描述：放置便于使用 RxJava 的一些工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class RxUtils {

    /** 默认需要弹窗 */
    public static <T> ObservableTransformer<T, T> applySchedulers(IView view) {
        return applySchedulers(view, true);
    }

    /** 设置是否需要弹窗 */
    public static <T> ObservableTransformer<T, T> applySchedulers(IView view, boolean isDialog) {
        return applySchedulers(view, isDialog, isDialog);
    }

    /** 设置是否需要显示隐藏弹窗 */
    public static <T> ObservableTransformer<T, T> applySchedulers(IView view, boolean isShowDialog, boolean isHideDialog) {
        return applySchedulers(view, isShowDialog, isHideDialog, true);
    }

    /** 设置是否需要显示隐藏弹窗、是否需要绑定生命界面周期 */
    public static <T> ObservableTransformer<T, T> applySchedulers(IView view, boolean isShowDialog, boolean isHideDialog, boolean isBindToLifecycle) {
        return observable -> {
            observable = observable
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(1, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .doOnSubscribe(disposable -> {
                        if (isShowDialog) {
                            view.showLoading(); // 显示进度条
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        if (isHideDialog) {
                            view.hideLoading(); // 隐藏进度条
                        }
                    });
            if (isBindToLifecycle) {
                observable = observable.compose(RxLifecycleUtils.bindToLifecycle(view));
            }
            return observable;
        };
    }

    /** 获取一个适用于 关闭TYRecyclerView 的回调意图 */
    public static Action getTYRVFinally(TYRecyclerView rView) {
        return () -> {
            try {
                if (rView.getWindowToken() != null) {
                    rView.complete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    /** 异步操作主线程回调 */
    public static <T> void asyncOperate(TYObservableOnSubscribe<T> tyObservableOnSubscribe, TYObserver<T> tyObserver) {
        asyncOperate((ObservableOnSubscribe<T>) tyObservableOnSubscribe, tyObserver);
    }

    /** 异步操作主线程回调 原始类 */
    public static <T> void asyncOperate(ObservableOnSubscribe<T> observableOnSubscribe, Observer<T> observer) {
        Observable.create(observableOnSubscribe)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    // =============================================================================================

    /** 自定义已调用onNext() 类 */
    public static class TYObservableOnSubscribe<T> implements ObservableOnSubscribe<T> {
        private T defaultT;

        public TYObservableOnSubscribe(T defaultT) {
            this.defaultT = defaultT;
        }

        @Override
        public void subscribe(ObservableEmitter<T> emitter) throws Exception {
            onAsyncTask(emitter);
            if (defaultT != null) {
                emitter.onNext(defaultT);
            }
        }

        public void onAsyncTask(ObservableEmitter<T> emitter) throws Exception {
        }
    }

    /** 自定义可只实现onNext() 类 */
    public interface TYObserver<T> extends Observer<T> {
        @Override
        default void onSubscribe(Disposable d) {
        }

        @Override
        default void onError(Throwable e) {
        }

        @Override
        default void onComplete() {
        }
    }
}
