package a.f.ui.imagebrowse;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 16:56
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */

@ActivityScope
@Component(modules = ImageBrowseModule.class, dependencies = AppComponent.class)
public interface ImageBrowseComponent {
    void inject(ImageBrowseActivity activity);
}