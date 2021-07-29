package a.f.ui.imagebrowse;

import com.jess.arms.base.BaseActivity_MembersInjector;

import javax.inject.Provider;

import dagger.MembersInjector;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 16:54
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public final class ImageBrowseActivity_MembersInjector implements MembersInjector<ImageBrowseActivity> {
    private final Provider<ImageBrowsePresenter> mPresenterProvider;

    public ImageBrowseActivity_MembersInjector(Provider<ImageBrowsePresenter> mPresenterProvider) {
        this.mPresenterProvider = mPresenterProvider;
    }

    public static MembersInjector<ImageBrowseActivity> create(Provider<ImageBrowsePresenter> mPresenterProvider) {
        return new ImageBrowseActivity_MembersInjector(mPresenterProvider);
    }

    @Override
    public void injectMembers(ImageBrowseActivity instance) {
        BaseActivity_MembersInjector.injectMPresenter(instance, this.mPresenterProvider.get());
    }
}
