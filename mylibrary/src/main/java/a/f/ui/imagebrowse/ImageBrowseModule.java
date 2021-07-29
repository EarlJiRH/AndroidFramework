package a.f.ui.imagebrowse;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

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
@Module
public class ImageBrowseModule {
    private ImageBrowseContract.View view;

    /**
     * 构建ImageBrowseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageBrowseModule(ImageBrowseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ImageBrowseContract.View provideImageBrowseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ImageBrowseContract.Model provideImageBrowseModel(ImageBrowseModel model) {
        return model;
    }
}
