package a.f.ui.imagebrowse;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 17:06
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class ImageBrowseModule_ProvideImageBrowseViewFactory implements Factory<ImageBrowseContract.View> {
    private final ImageBrowseModule module;

    public ImageBrowseModule_ProvideImageBrowseViewFactory(ImageBrowseModule module) {
        this.module = module;
    }

    @Override
    public ImageBrowseContract.View get() {
        return provideImageBrowseView(this.module);
    }

    public static ImageBrowseModule_ProvideImageBrowseViewFactory create(ImageBrowseModule module) {
        return new ImageBrowseModule_ProvideImageBrowseViewFactory(module);
    }

    public static ImageBrowseContract.View provideImageBrowseView(ImageBrowseModule instance) {
        return (ImageBrowseContract.View) Preconditions.checkNotNull(instance.provideImageBrowseView(), "Cannot return null from a non-@Nullable @Provides method");
    }
}