package a.f.ui.imagebrowse;

import android.app.Application;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;

import javax.inject.Provider;

import dagger.MembersInjector;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 17:07
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class ImageBrowsePresenter_MembersInjector implements MembersInjector<ImageBrowsePresenter> {
    private final Provider<RxErrorHandler> mErrorHandlerProvider;
    private final Provider<Application> mApplicationProvider;
    private final Provider<ImageLoader> mImageLoaderProvider;
    private final Provider<AppManager> mAppManagerProvider;

    public ImageBrowsePresenter_MembersInjector(Provider<RxErrorHandler> mErrorHandlerProvider, Provider<Application> mApplicationProvider, Provider<ImageLoader> mImageLoaderProvider, Provider<AppManager> mAppManagerProvider) {
        this.mErrorHandlerProvider = mErrorHandlerProvider;
        this.mApplicationProvider = mApplicationProvider;
        this.mImageLoaderProvider = mImageLoaderProvider;
        this.mAppManagerProvider = mAppManagerProvider;
    }

    public static MembersInjector<ImageBrowsePresenter> create(Provider<RxErrorHandler> mErrorHandlerProvider, Provider<Application> mApplicationProvider, Provider<ImageLoader> mImageLoaderProvider, Provider<AppManager> mAppManagerProvider) {
        return new ImageBrowsePresenter_MembersInjector(mErrorHandlerProvider, mApplicationProvider, mImageLoaderProvider, mAppManagerProvider);
    }

    @Override
    public void injectMembers(ImageBrowsePresenter instance) {
        injectMErrorHandler(instance, (RxErrorHandler)this.mErrorHandlerProvider.get());
        injectMApplication(instance, (Application)this.mApplicationProvider.get());
        injectMImageLoader(instance, (ImageLoader)this.mImageLoaderProvider.get());
        injectMAppManager(instance, (AppManager)this.mAppManagerProvider.get());
    }

    public static void injectMErrorHandler(ImageBrowsePresenter instance, RxErrorHandler mErrorHandler) {
        instance.mErrorHandler = mErrorHandler;
    }

    public static void injectMApplication(ImageBrowsePresenter instance, Application mApplication) {
        instance.mApplication = mApplication;
    }

    public static void injectMImageLoader(ImageBrowsePresenter instance, ImageLoader mImageLoader) {
        instance.mImageLoader = mImageLoader;
    }

    public static void injectMAppManager(ImageBrowsePresenter instance, AppManager mAppManager) {
        instance.mAppManager = mAppManager;
    }
}