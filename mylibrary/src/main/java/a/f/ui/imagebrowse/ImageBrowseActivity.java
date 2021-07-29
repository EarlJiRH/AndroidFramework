package a.f.ui.imagebrowse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.Preconditions;
import com.yanzhenjie.album.widget.photoview.FixViewPager;

import java.util.ArrayList;
import java.util.List;

import a.f.R;
import a.f.R2;
import a.f.bean.common.judgement.SelectBean;
import a.f.utils.AFImageUtils;
import a.f.utils.AFVariableUtils;
import a.f.utils.RxUtils;
import a.f.utils.ToastUtils;
import a.f.utils.constant.AFSF;
import a.f.widget.popup.SelectDialog;
import butterknife.BindView;
import io.reactivex.ObservableEmitter;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 16:51
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class ImageBrowseActivity extends BaseActivity<ImageBrowsePresenter> implements ImageBrowseContract.View {

    @BindView(R2.id.imageBrowseViewPager)
    FixViewPager imageBrowseViewPager;
    @BindView(R2.id.imageBrowseIndex)
    TextView imageBrowseIndex;
    private static String mServersUrl;
    public static final String KEY_IMAGE_PATH_LIST = "image_path_list";
    public static final String KEY_IMAGE_DEFAULT_POSITION = "image_default_position";
    public static final String KEY_IMAGE_SERVERS_URL = "image_servers_url";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerImageBrowseComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .imageBrowseModule(new ImageBrowseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_image_browse;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        List<String> imagePathList = getIntent().getStringArrayListExtra(KEY_IMAGE_PATH_LIST);
        if (imagePathList == null || imagePathList.isEmpty()) {
            killMyself();
            return;
        }

        mServersUrl = getIntent().getStringExtra(KEY_IMAGE_SERVERS_URL);

        int defaultPosition = getIntent().getIntExtra(KEY_IMAGE_DEFAULT_POSITION, 0);
        if (defaultPosition < 0) {
            defaultPosition = 0;
        } else if (defaultPosition >= imagePathList.size()) {
            defaultPosition = imagePathList.size() - 1;
        }

        imageBrowseViewPager.setAdapter(new ImageBrowseAdapter(getSupportFragmentManager(), imagePathList));
        imageBrowseViewPager.setCurrentItem(defaultPosition);
        imageBrowseIndex.setText((defaultPosition + 1) + "/" + imagePathList.size());

        imageBrowseViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                imageBrowseIndex.setText((position + 1) + "/" + imagePathList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtils.getInstance().showToast(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        Preconditions.checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onDestroy() {
        mServersUrl = null;
        super.onDestroy();
    }

    // 以下是定义的内部类 ============================================================================================================

    /** 图片浏览 Adapter */
    public static class ImageBrowseAdapter extends FragmentStatePagerAdapter {

        private List<String> images;

        public ImageBrowseAdapter(FragmentManager fm, List<String> images) {
            super(fm);
            this.images = images == null ? new ArrayList<>() : images;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString(ImageBrowseFragment.KEY_IMAGE_PATH, images.get(position));
            ImageBrowseFragment imageBrowseFragment = new ImageBrowseFragment();
            imageBrowseFragment.setArguments(bundle);
            return imageBrowseFragment;
        }

        @Override
        public int getCount() {
            return images.size();
        }

    }

    /** 图片浏览 Fragment */
    public static class ImageBrowseFragment extends Fragment implements PhotoViewAttacher.OnViewTapListener, View.OnLongClickListener {

        public static final String KEY_IMAGE_PATH = "image_path";
        private Activity activity;
        private ProgressDialog progressDialog;
        private PhotoView imageBrowsePhotoView;
        private String imagePath;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            activity = AppManager.getAppManager().getTopActivity();
            View rootView = inflater.inflate(R.layout.fragment_image_browse, container, false);
            imageBrowsePhotoView = rootView.findViewById(R.id.imageBrowsePhotoView);
            imagePath = getArguments().getString(KEY_IMAGE_PATH);
            if (!TextUtils.isEmpty(mServersUrl) && !imagePath.startsWith("http")) {
                imagePath = mServersUrl + imagePath;
            }

            Glide.with(this)
                    .load(imagePath)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            imageBrowsePhotoView.setScaleType(ImageView.ScaleType.CENTER);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            imageBrowsePhotoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                            return false;
                        }
                    })
                    .apply(new RequestOptions().error(R.mipmap.image_default).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(imageBrowsePhotoView);
            imageBrowsePhotoView.setOnViewTapListener(this);
            imageBrowsePhotoView.setOnLongClickListener(this);
            return rootView;
        }

        @Override
        public void onViewTap(View view, float x, float y) {
            AppManager.getAppManager().killActivity(ImageBrowseActivity.class);
        }

        @Override
        public boolean onLongClick(View v) {
            new SelectDialog(activity, AFVariableUtils.getImageOperation(), values -> {
                SelectBean selectBean = (SelectBean) values.get(0);
                if (selectBean.getNumberI() == 1) { // 保存图片
                    controlShield(true);
                    RxUtils.asyncOperate(
                            new RxUtils.TYObservableOnSubscribe<String>(AFSF.S_FS) {
                                @Override
                                public void onAsyncTask(ObservableEmitter<String> emitter) throws Exception {
                                    Bitmap bitmap = Glide.with(ImageBrowseFragment.this)
                                            .asBitmap()
                                            .load(imagePath)
                                            .submit()
                                            .get();
                                    AFImageUtils.getInstance().saveImageToDCIM(bitmap);
                                }
                            },
                            s -> controlShield(false)
                    );
                }
            }).show();
            return false;
        }

        /** 控制进度弹窗 */
        private void controlShield(boolean isShow) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            if (isShow) {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setMessage("正在保存中...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        }

    }

}
