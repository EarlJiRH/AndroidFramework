package a.f.ui.imagebrowse;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity_MembersInjector;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;

import javax.inject.Provider;

import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 16:49
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class DaggerImageBrowseComponent implements ImageBrowseComponent {
    private Provider<IRepositoryManager> repositoryManagerProvider;
    private Provider<Gson> gsonProvider;
    private Provider<Application> applicationProvider;
    private Provider<ImageBrowseModel> imageBrowseModelProvider;
    private Provider<ImageBrowseContract.Model> provideImageBrowseModelProvider;
    private Provider<ImageBrowseContract.View> provideImageBrowseViewProvider;
    private Provider<RxErrorHandler> rxErrorHandlerProvider;
    private Provider<ImageLoader> imageLoaderProvider;
    private Provider<AppManager> appManagerProvider;
    private Provider<ImageBrowsePresenter> imageBrowsePresenterProvider;

    private DaggerImageBrowseComponent(ImageBrowseModule imageBrowseModuleParam, AppComponent appComponentParam) {
        this.initialize(imageBrowseModuleParam, appComponentParam);
    }

    public static DaggerImageBrowseComponent.Builder builder() {
        return new DaggerImageBrowseComponent.Builder();
    }

    private void initialize(ImageBrowseModule imageBrowseModuleParam, AppComponent appComponentParam) {
        this.repositoryManagerProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_repositoryManager(appComponentParam);
        this.gsonProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_gson(appComponentParam);
        this.applicationProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_application(appComponentParam);
        this.imageBrowseModelProvider = DoubleCheck.provider(ImageBrowseModel_Factory.create(this.repositoryManagerProvider, this.gsonProvider, this.applicationProvider));
        this.provideImageBrowseModelProvider = DoubleCheck.provider(ImageBrowseModule_ProvideImageBrowseModelFactory.create(imageBrowseModuleParam, this.imageBrowseModelProvider));
        this.provideImageBrowseViewProvider = DoubleCheck.provider(ImageBrowseModule_ProvideImageBrowseViewFactory.create(imageBrowseModuleParam));
        this.rxErrorHandlerProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_rxErrorHandler(appComponentParam);
        this.imageLoaderProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_imageLoader(appComponentParam);
        this.appManagerProvider = new DaggerImageBrowseComponent.com_jess_arms_di_component_AppComponent_appManager(appComponentParam);
        this.imageBrowsePresenterProvider = DoubleCheck.provider(ImageBrowsePresenter_Factory.create(this.provideImageBrowseModelProvider, this.provideImageBrowseViewProvider, this.rxErrorHandlerProvider, this.applicationProvider, this.imageLoaderProvider, this.appManagerProvider));
    }

    @Override
    public void inject(ImageBrowseActivity activity) {
        this.injectImageBrowseActivity(activity);
    }

    private ImageBrowseActivity injectImageBrowseActivity(ImageBrowseActivity instance) {
        BaseActivity_MembersInjector.injectMPresenter(instance, this.imageBrowsePresenterProvider.get());
        return instance;
    }

    private static class com_jess_arms_di_component_AppComponent_appManager implements Provider<AppManager> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_appManager(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public AppManager get() {
            return (AppManager)Preconditions.checkNotNull(this.appComponent.appManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_jess_arms_di_component_AppComponent_imageLoader implements Provider<ImageLoader> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_imageLoader(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public ImageLoader get() {
            return (ImageLoader)Preconditions.checkNotNull(this.appComponent.imageLoader(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_jess_arms_di_component_AppComponent_rxErrorHandler implements Provider<RxErrorHandler> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_rxErrorHandler(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public RxErrorHandler get() {
            return (RxErrorHandler)Preconditions.checkNotNull(this.appComponent.rxErrorHandler(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_jess_arms_di_component_AppComponent_application implements Provider<Application> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_application(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public Application get() {
            return (Application)Preconditions.checkNotNull(this.appComponent.application(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_jess_arms_di_component_AppComponent_gson implements Provider<Gson> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_gson(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public Gson get() {
            return (Gson)Preconditions.checkNotNull(this.appComponent.gson(), "Cannot return null from a non-@Nullable component method");
        }
    }

    private static class com_jess_arms_di_component_AppComponent_repositoryManager implements Provider<IRepositoryManager> {
        private final AppComponent appComponent;

        com_jess_arms_di_component_AppComponent_repositoryManager(AppComponent appComponent) {
            this.appComponent = appComponent;
        }

        @Override
        public IRepositoryManager get() {
            return (IRepositoryManager)Preconditions.checkNotNull(this.appComponent.repositoryManager(), "Cannot return null from a non-@Nullable component method");
        }
    }

    public static final class Builder {
        private ImageBrowseModule imageBrowseModule;
        private AppComponent appComponent;

        private Builder() {
        }

        public DaggerImageBrowseComponent.Builder imageBrowseModule(ImageBrowseModule imageBrowseModule) {
            this.imageBrowseModule = (ImageBrowseModule)Preconditions.checkNotNull(imageBrowseModule);
            return this;
        }

        public DaggerImageBrowseComponent.Builder appComponent(AppComponent appComponent) {
            this.appComponent = (AppComponent)Preconditions.checkNotNull(appComponent);
            return this;
        }

        public ImageBrowseComponent build() {
            Preconditions.checkBuilderRequirement(this.imageBrowseModule, ImageBrowseModule.class);
            Preconditions.checkBuilderRequirement(this.appComponent, AppComponent.class);
            return new DaggerImageBrowseComponent(this.imageBrowseModule, this.appComponent);
        }
    }
}