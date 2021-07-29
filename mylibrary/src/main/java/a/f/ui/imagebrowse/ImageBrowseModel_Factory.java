package a.f.ui.imagebrowse;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;

import javax.inject.Provider;

import dagger.internal.Factory;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 17:02
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class ImageBrowseModel_Factory implements Factory<ImageBrowseModel> {
    private final Provider<IRepositoryManager> repositoryManagerProvider;
    private final Provider<Gson> mGsonProvider;
    private final Provider<Application> mApplicationProvider;

    public ImageBrowseModel_Factory(Provider<IRepositoryManager> repositoryManagerProvider, Provider<Gson> mGsonProvider, Provider<Application> mApplicationProvider) {
        this.repositoryManagerProvider = repositoryManagerProvider;
        this.mGsonProvider = mGsonProvider;
        this.mApplicationProvider = mApplicationProvider;
    }

    @Override
    public ImageBrowseModel get() {
        ImageBrowseModel instance = new ImageBrowseModel((IRepositoryManager)this.repositoryManagerProvider.get());
        ImageBrowseModel_MembersInjector.injectMGson(instance, (Gson)this.mGsonProvider.get());
        ImageBrowseModel_MembersInjector.injectMApplication(instance, (Application)this.mApplicationProvider.get());
        return instance;
    }

    public static ImageBrowseModel_Factory create(Provider<IRepositoryManager> repositoryManagerProvider, Provider<Gson> mGsonProvider, Provider<Application> mApplicationProvider) {
        return new ImageBrowseModel_Factory(repositoryManagerProvider, mGsonProvider, mApplicationProvider);
    }

    public static ImageBrowseModel newInstance(IRepositoryManager repositoryManager) {
        return new ImageBrowseModel(repositoryManager);
    }
}