package a.f.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.SparseArray;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import a.f.R;
import a.f.base.BaseApp;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:28
 * 描述：图片 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class AFImageUtils {

    private static volatile AFImageUtils mInstance; // 本类实例
    protected SparseArray<RequestOptions> saROs; // 高性能 存储 RequestOptions
    protected SparseArray<DrawableTransitionOptions> saDTOs; // 高性能 存储 DrawableTransitionOptions

    protected AFImageUtils() {
        saROs = new SparseArray<>();
        saDTOs = new SparseArray<>();
    }

    /** 获取 AFImageUtils 的实例 */
    public static AFImageUtils getInstance() {
        if (mInstance == null) {
            synchronized (AFImageUtils.class) {
                if (mInstance == null) {
                    mInstance = new AFImageUtils();
                }
            }
        }
        return mInstance;
    }

    /** 验证是否是图片 */
    public boolean isImageFormat(String value) {
        boolean isImageFormat = false;
        try {
            if (!TextUtils.isEmpty(value)) {
                value = value.toLowerCase();
                for (String imageFormat : AFSF.IMAGE_FORMAT) {
                    if (value.endsWith(imageFormat)) {
                        isImageFormat = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isImageFormat;
    }

    /** 获取临时图片文件 默认后缀.png */
    public File getTempImageFile() {
        return getTempImageFile(AFSF.PATH_FOLDER_PHOTO, AFSF.IMAGE_FORMAT[2]);
    }

    /** 获取临时图片文件 */
    public File getTempImageFile(String dir, String fileSuffix) {
        return SystemUtils.getTempFile(dir, null, null, fileSuffix);
    }

    /** 保存图片到本地相册 */
    public boolean saveImageToDCIM(Bitmap bitmap) {
        File imageFile = null;
        boolean isSuccess = false;
        try {
            String dcimPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + AFSF.S_FS + AFSF.FOLDER_IMAGE_CACHE;
            File imagesDir = new File(dcimPath);
            if (!imagesDir.exists()) {
                if (!imagesDir.mkdirs()) {
                    throw new NullPointerException("创建目录失败");
                }
            }

            imageFile = new File(imagesDir, UUID.randomUUID().toString() + AFSF.IMAGE_FORMAT[2]);
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            isSuccess = imageFile.exists();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (isSuccess) {
                ToastUtils.getInstance().showToast(R.string.hintSaveSuccess);
                BaseApp.getI().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile))); // 通知图库更新
            } else {
                ToastUtils.getInstance().showToast(R.string.hintSaveFailed);
            }
        }
        return isSuccess;
    }
}
