package a.f.ui.imagebrowse;

import javax.inject.Provider;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 17:05
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class ImageBrowseModule_ProvideImageBrowseModelFactory implements Factory<ImageBrowseContract.Model> {
    private final ImageBrowseModule module;
    private final Provider<ImageBrowseModel> modelProvider;

    public ImageBrowseModule_ProvideImageBrowseModelFactory(ImageBrowseModule module, Provider<ImageBrowseModel> modelProvider) {
        this.module = module;
        this.modelProvider = modelProvider;
    }

    @Override
    public ImageBrowseContract.Model get() {
        return provideImageBrowseModel(this.module, (ImageBrowseModel)this.modelProvider.get());
    }

    public static ImageBrowseModule_ProvideImageBrowseModelFactory create(ImageBrowseModule module, Provider<ImageBrowseModel> modelProvider) {
        return new ImageBrowseModule_ProvideImageBrowseModelFactory(module, modelProvider);
    }

    public static ImageBrowseContract.Model provideImageBrowseModel(ImageBrowseModule instance, ImageBrowseModel model) {
        return (ImageBrowseContract.Model) Preconditions.checkNotNull(instance.provideImageBrowseModel(model), "Cannot return null from a non-@Nullable @Provides method");
    }
}