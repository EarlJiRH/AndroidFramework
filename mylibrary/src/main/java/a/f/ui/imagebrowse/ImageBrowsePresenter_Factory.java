package a.f.ui.imagebrowse;

import android.app.Application;

import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;

import javax.inject.Provider;

import dagger.internal.Factory;
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
public final class ImageBrowsePresenter_Factory implements Factory<ImageBrowsePresenter> {
    private final Provider<ImageBrowseContract.Model> modelProvider;
    private final Provider<ImageBrowseContract.View> rootViewProvider;
    private final Provider<RxErrorHandler> mErrorHandlerProvider;
    private final Provider<Application> mApplicationProvider;
    private final Provider<ImageLoader> mImageLoaderProvider;
    private final Provider<AppManager> mAppManagerProvider;

    public ImageBrowsePresenter_Factory(Provider<ImageBrowseContract.Model> modelProvider, Provider<ImageBrowseContract.View> rootViewProvider, Provider<RxErrorHandler> mErrorHandlerProvider, Provider<Application> mApplicationProvider, Provider<ImageLoader> mImageLoaderProvider, Provider<AppManager> mAppManagerProvider) {
        this.modelProvider = modelProvider;
        this.rootViewProvider = rootViewProvider;
        this.mErrorHandlerProvider = mErrorHandlerProvider;
        this.mApplicationProvider = mApplicationProvider;
        this.mImageLoaderProvider = mImageLoaderProvider;
        this.mAppManagerProvider = mAppManagerProvider;
    }

    @Override
    public ImageBrowsePresenter get() {
        ImageBrowsePresenter instance = new ImageBrowsePresenter((ImageBrowseContract.Model)this.modelProvider.get(), (ImageBrowseContract.View)this.rootViewProvider.get());
        ImageBrowsePresenter_MembersInjector.injectMErrorHandler(instance, (RxErrorHandler)this.mErrorHandlerProvider.get());
        ImageBrowsePresenter_MembersInjector.injectMApplication(instance, (Application)this.mApplicationProvider.get());
        ImageBrowsePresenter_MembersInjector.injectMImageLoader(instance, (ImageLoader)this.mImageLoaderProvider.get());
        ImageBrowsePresenter_MembersInjector.injectMAppManager(instance, (AppManager)this.mAppManagerProvider.get());
        return instance;
    }

    public static ImageBrowsePresenter_Factory create(Provider<ImageBrowseContract.Model> modelProvider, Provider<ImageBrowseContract.View> rootViewProvider, Provider<RxErrorHandler> mErrorHandlerProvider, Provider<Application> mApplicationProvider, Provider<ImageLoader> mImageLoaderProvider, Provider<AppManager> mAppManagerProvider) {
        return new ImageBrowsePresenter_Factory(modelProvider, rootViewProvider, mErrorHandlerProvider, mApplicationProvider, mImageLoaderProvider, mAppManagerProvider);
    }

    public static ImageBrowsePresenter newInstance(ImageBrowseContract.Model model, ImageBrowseContract.View rootView) {
        return new ImageBrowsePresenter(model, rootView);
    }
}