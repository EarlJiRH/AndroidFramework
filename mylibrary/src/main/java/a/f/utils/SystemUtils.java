package a.f.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;

import androidx.core.content.FileProvider;

import com.jess.arms.utils.ArmsUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import a.f.R;
import a.f.base.BaseApp;
import a.f.utils.callback.CallBack;
import a.f.utils.constant.AFConfig;
import a.f.utils.constant.AFSF;
import a.f.utils.filefilter.DeleteFileFilter;
import io.reactivex.ObservableEmitter;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 15:30
 * 描述：系统/硬件 工具类
 * 修改人：
 * 修改时间：Containing repo
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SystemUtils {

    /** 安装 apk */
    //  【兼容 API24+ StrictMode API 规则】
    //  1.添加配置代码：
    //  <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
    //  <provider
    //      android:name="android.support.v4.content.FileProvider"
    //      android:authorities="${applicationId}.fileprovider"
    //      android:exported="false"
    //      android:grantUriPermissions="true"
    //      tools:replace="android:name,android:authorities,android:exported,android:grantUriPermissions">
    //      <meta-data
    //          android:name="android.support.FILE_PROVIDER_PATHS"
    //          android:resource="@xml/file_paths"
    //          tools:replace="android:name,android:resource"/>
    //   </provider>
    //   2.引入文件：
    //   app/src/main/res/xml/file_paths
    public static void installApk(String filePath) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uriData;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                uriData = FileProvider.getUriForFile(BaseApp.getI(), AFConfig.APP_PACKAGE_NAME + ".fileprovider", new File(filePath));
            } else {
                uriData = Uri.fromFile(new File(filePath));
            }

            intent.setDataAndType(uriData, "application/vnd.android.package-archive");
            ArmsUtils.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 卸载 apk */
    public static void uninstallApk(String packageName) {
        try {
            ArmsUtils.startActivity(new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 判断是否已经安装指定App */
    public static boolean isAppInstalled(String packageName) {
        PackageManager packageManager = BaseApp.getI().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos.size() > 0) {
            for (PackageInfo packageInfo : packageInfos) {
                if (TextUtils.equals(packageName, packageInfo.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 获取 某个 META_DATA 的内容 */
    public static String getMetaData(String dataName) {
        return getMetaData(AFConfig.APP_PACKAGE_NAME, dataName);
    }

    /** 获取 某个 META_DATA 的内容 */
    public static String getMetaData(String packageName, String dataName) {
        try {
            return BaseApp.getI().getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData.getString(dataName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件是否存在
     * <p>
     * 所需权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static boolean isFileExists(String filePath) {
        boolean isExist = false;
        try {
            isExist = new File(filePath).exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     * 批量删除文件
     * <p>
     * 所需权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void deleteFiles(List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            return;
        }

        for (String path : paths) {
            try {
                FileUtils.listFiles(new File(path), new DeleteFileFilter(), TrueFileFilter.INSTANCE);
                L.d("deleteFiles() == 执行删除成功：" + path);
            } catch (Exception e) {
                L.d("deleteFiles() == 执行删除失败：" + path);
            }
        }
    }

    /**
     * 递归删除文件夹里所有文件
     * <p>
     * 所需权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static void deleteFolderFiles(List<File> fileRaws, CallBack callBack) {
        RxUtils.asyncOperate(
                new RxUtils.TYObservableOnSubscribe<String>(AFSF.S_FS) {
                    @Override
                    public void onAsyncTask(ObservableEmitter<String> emitter) {
                        for (File fileRaw : fileRaws) {
                            deleteFolderFiles(fileRaw);
                        }
                    }
                },
                s -> {
                    if (callBack != null) {
                        callBack.onBack();
                    }
                }
        );
    }

    /** 执行 递归删除文件夹里所有文件 */
    private static void deleteFolderFiles(File fileRaw) {
        try {
            if (!fileRaw.exists()) {
                L.d("不存在：" + fileRaw.getAbsolutePath());
                return;
            } else if (fileRaw.isFile()) {
                try {
                    L.d("删除文件：" + fileRaw.getAbsolutePath() + " == 结果：" + fileRaw.delete());
                } catch (Exception ignored) {
                }
                return;
            } else if (!fileRaw.isDirectory()) {
                L.d("不是目录：" + fileRaw.getAbsolutePath());
                return;
            }

            File[] files = fileRaw.listFiles();
            if (files == null) {
                try {
                    L.d("获取子目录失败：" + fileRaw.getAbsolutePath());
                    L.d("删除目录：" + fileRaw.getAbsolutePath() + " == 结果：" + fileRaw.delete());
                } catch (Exception ignored) {
                }
                return;
            }

            for (File file : files) {
                deleteFolderFiles(file);
            }

            try {
                L.d("删除目录：" + fileRaw.getAbsolutePath() + " == 结果：" + fileRaw.delete());
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取临时文件【dir = 目录】【namePrefix = 名称前缀】【nameSuffix = 名称后缀】【fileSuffix = 文件后缀】
     * <p>
     * 所需权限：<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     */
    public static File getTempFile(String dir, String namePrefix, String nameSuffix, String fileSuffix) {
        namePrefix = TextUtils.isEmpty(namePrefix) ? "" : namePrefix;
        nameSuffix = TextUtils.isEmpty(nameSuffix) ? "" : nameSuffix;
        fileSuffix = TextUtils.isEmpty(fileSuffix) ? "" : fileSuffix;

        boolean isMkdirs = true;
        File folder = new File(dir);
        if (!folder.exists()) {
            isMkdirs = folder.mkdirs();
        }

        if (isMkdirs) {
            return new File(folder, namePrefix + UUID.randomUUID().toString() + nameSuffix + fileSuffix);
        } else {
            ToastUtils.getInstance().showToast(R.string.hintCreateDirFailed, false);
            return null;
        }
    }

    /** 获取 App版本 名称 */
    public static String getVersionName() {
        try {
            return BaseApp.getI().getPackageManager().getPackageInfo(BaseApp.getI().getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取 App版本 号 */
    public static int getVersionCode() {
        try {
            return BaseApp.getI().getPackageManager().getPackageInfo(BaseApp.getI().getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取 手机设备号
     * <p>
     * 所需权限：<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String getDeviceId() {
        String deviceId = "未知";
        try {
            TelephonyManager telephonyManager = (TelephonyManager) BaseApp.getI().getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                deviceId = telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /** 获取设备型号 */
    public static String getDeviceModel() {
        String model = Build.MODEL;
        if (TextUtils.isEmpty(model)) {
            model = "";
        } else {
            model = model.trim().replaceAll("\\s*", "");
        }
        return model;
    }

    /** 获取AndroidId */
    @SuppressLint("HardwareIds")
    public static String getAndroidId() {
        try {
            return Settings.Secure.getString(BaseApp.getI().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /** 实现文本复制功能 */
    public static void copyText(String text) {
        try {
            // 得到剪贴板管理器
            ClipboardManager clipboardManager = (ClipboardManager) BaseApp.getI().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager == null) {
                return;
            }

            ClipData clipData = ClipData.newPlainText("text", text);
            clipboardManager.setPrimaryClip(clipData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 实现粘贴功能 */
    public static String pasteText() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 得到剪贴板管理器
            ClipboardManager clipboardManager = (ClipboardManager) BaseApp.getI().getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboardManager != null) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                int count = clipData.getItemCount();
                for (int i = 0; i < count; i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    CharSequence cs = item.coerceToText(BaseApp.getI());
                    stringBuilder.append(cs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /** 获取当前进程名称 */
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager == null) {
            return null;
        }

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == pid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    /** 获取App图标（兼容API26+） */
    public static Bitmap getAppIcon(Drawable originalAppIcon) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return ((BitmapDrawable) originalAppIcon).getBitmap();
            } else {
                if (originalAppIcon instanceof BitmapDrawable) {
                    return ((BitmapDrawable) originalAppIcon).getBitmap();
                } else if (originalAppIcon instanceof AdaptiveIconDrawable) {
                    Drawable backgroundDr = ((AdaptiveIconDrawable) originalAppIcon).getBackground();
                    Drawable foregroundDr = ((AdaptiveIconDrawable) originalAppIcon).getForeground();
                    Drawable[] dra = new Drawable[]{backgroundDr, foregroundDr};

                    LayerDrawable layerDrawable = new LayerDrawable(dra);
                    int width = layerDrawable.getIntrinsicWidth();
                    int height = layerDrawable.getIntrinsicHeight();

                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);

                    layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    layerDrawable.draw(canvas);

                    return bitmap;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 获取状态栏高度 */
    public static int getStatusBarHeight() {
        // 默认高度24dp
        int sbHeight = dpToPx(24);

        try {
            int resourceId = BaseApp.getI().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                sbHeight = BaseApp.getI().getResources().getDimensionPixelSize(resourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sbHeight;
    }

    /** 检查后台服务是否存活 */
    public static boolean checkServiceAlive(String className) {
        boolean isAlive = false;
        try {
            ActivityManager activityManager = (ActivityManager) BaseApp.getI().getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> amRsiList = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (amRsiList != null && !amRsiList.isEmpty()) {
                for (ActivityManager.RunningServiceInfo fsi : amRsiList) {
                    if (TextUtils.equals(fsi.service.getClassName(), className)) {
                        isAlive = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAlive;
    }

    /** dp 转为 像素 ： 适用于控件单位 */
    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    /** sp 转为 像素 ： 适用于字体单位 */
    public static int spToPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    /** 像素 转为 dp ： 适用于控件单位 */
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /** 像素 转为 sp ： 适用于字体单位 */
    public static int pxToSp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().scaledDensity);
    }

    /**
     * 检查是否开启了位置服务
     *
     * @param isHintEnabled 当需要用户开启位置服务时，是否提示用户开启位置服务
     * @return 【true=已启用】【false=未启用】；如果SDK小于6.0(23)则默认已开启
     */
    public static boolean isEnabledLocation(boolean isHintEnabled) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        LocationManager locationManager = (LocationManager) BaseApp.getI().getSystemService(Context.LOCATION_SERVICE);
        boolean isEnabled = locationManager != null && (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));

        if (isHintEnabled && !isEnabled) {
            WidgetUtils.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
        return isEnabled;
    }

    /** 深度复制对象 */
    @SuppressWarnings("unchecked")
    public static <T> T copyObject(T obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 稀释数据
     *
     * @param maxSize        所需数据最大长度
     * @param lastKeepSize   保留最后一段精确的数据的长度
     * @param originDataList 原始数据集合；建议传入子类型为ArrayList的正序List
     */
    public static <T> List<T> dilutionData(int maxSize, int lastKeepSize, List<T> originDataList) {
        if (originDataList == null) {
            return new ArrayList<>();
        }

        int originDataListSize = originDataList.size();
        if (originDataListSize <= maxSize) {
            return originDataList;
        }
        // 需要计算的总数据大小
        int needComputeSize = originDataListSize - lastKeepSize;
        // 实际需要数据的最大长度，减去了最后保留长度
        int realityMaxSize = maxSize - lastKeepSize;

        // 间隔
        int intervalSize = needComputeSize / realityMaxSize;

        // 开始稀释数据
        List<T> dilutionDataList = new ArrayList<>();
        for (int i = 0; i < needComputeSize; i += intervalSize) {
            dilutionDataList.add(originDataList.get(i));
        }

        // 删除多余的数据
        while (true) {
            if (dilutionDataList.size() > realityMaxSize) {
                dilutionDataList.remove(dilutionDataList.size() - 1);
            } else {
                break;
            }
        }

        // 添加最后一段精确的数据
        for (int i = originDataListSize - lastKeepSize; i < originDataListSize; i++) {
            dilutionDataList.add(originDataList.get(i));
        }

        return dilutionDataList;
    }
}
