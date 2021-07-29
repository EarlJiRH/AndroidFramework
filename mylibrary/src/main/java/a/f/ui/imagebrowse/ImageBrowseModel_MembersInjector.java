package a.f.ui.imagebrowse;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Provider;

import dagger.MembersInjector;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 17:04
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ImageBrowseModel_MembersInjector implements MembersInjector<ImageBrowseModel> {
    private final Provider<Gson> mGsonProvider;
    private final Provider<Application> mApplicationProvider;

    public ImageBrowseModel_MembersInjector(Provider<Gson> mGsonProvider, Provider<Application> mApplicationProvider) {
        this.mGsonProvider = mGsonProvider;
        this.mApplicationProvider = mApplicationProvider;
    }

    public static MembersInjector<ImageBrowseModel> create(Provider<Gson> mGsonProvider, Provider<Application> mApplicationProvider) {
        return new ImageBrowseModel_MembersInjector(mGsonProvider, mApplicationProvider);
    }

    @Override
    public void injectMembers(ImageBrowseModel instance) {
        injectMGson(instance, (Gson)this.mGsonProvider.get());
        injectMApplication(instance, (Application)this.mApplicationProvider.get());
    }

    public static void injectMGson(ImageBrowseModel instance, Gson mGson) {
        instance.mGson = mGson;
    }

    public static void injectMApplication(ImageBrowseModel instance, Application mApplication) {
        instance.mApplication = mApplication;
    }
}
