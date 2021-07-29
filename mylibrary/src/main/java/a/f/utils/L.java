package a.f.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import a.f.base.BaseApp;
import a.f.bean.dao.LogEntity;
import a.f.bean.dao.LogEntityDao;
import a.f.utils.constant.AFConfig;
import a.f.utils.constant.AFSF;
import a.f.utils.db.DBUtils;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 16:35
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class L {

    private static final String TAG = AFConfig.LOG_TAG;
    private static boolean mIsLogPrint = AFConfig.LOG_PRINT; // 是否需要打印日志
    private static boolean mIsLogWriteUpload = AFConfig.LOG_WRITE_UPLOAD; // 是否需要写日志并上传
    private static LogEntityDao mLogEntityDao = DBUtils.getDaoSession().getLogEntityDao();
    private static String mUserKey; // 用户标识，可传例子:UserUtils.getInstance().getUser().getUserId()
    private static String mDeviceId; // 设备ID
    private static String mSoftwareName = AFConfig.APP_NAME;
    private static String mVersionName = SystemUtils.getVersionName();
    private static int mVersionCode = SystemUtils.getVersionCode();
    private static DisplayMetrics mDisplayMetrics = BaseApp.getI().getResources().getDisplayMetrics();

    public static String getTag() {
        return TAG;
    }

    /** 设置用户标识 */
    public static void setUserKey(String userKey) {
        mUserKey = userKey;
    }

    // <editor-fold> 设置是否记录日志 ================================================================
    public static boolean isLogPrint() {
        return mIsLogPrint;
    }

    public static void setLogPrint(boolean logPrint) {
        mIsLogPrint = logPrint;
    }

    public static boolean isLogWriteUpload() {
        return mIsLogWriteUpload;
    }

    public static void setLogWriteUpload(boolean logWriteUpload) {
        mIsLogWriteUpload = logWriteUpload;
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> 打印日志所有方法 ================================================================
    public static void v(String msg) {
        if (mIsLogPrint) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (mIsLogPrint) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (mIsLogPrint) {
            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (mIsLogPrint) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (mIsLogPrint) {
            Log.e(TAG, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (mIsLogPrint) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (mIsLogPrint) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (mIsLogPrint) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (mIsLogPrint) {
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (mIsLogPrint) {
            Log.i(tag, msg);
        }
    }

    public static void vMulti(Object... msg) {
        if (mIsLogPrint) {
            Log.v(TAG, GsonUtils.getInstance().getGson().toJson(msg));
        }
    }

    public static void dMulti(Object... msg) {
        if (mIsLogPrint) {
            Log.d(TAG, GsonUtils.getInstance().getGson().toJson(msg));
        }
    }

    public static void iMulti(Object... msg) {
        if (mIsLogPrint) {
            Log.i(TAG, GsonUtils.getInstance().getGson().toJson(msg));
        }
    }

    public static void wMulti(Object... msg) {
        if (mIsLogPrint) {
            Log.w(TAG, GsonUtils.getInstance().getGson().toJson(msg));
        }
    }

    public static void eMulti(Object... msg) {
        if (mIsLogPrint) {
            Log.e(TAG, GsonUtils.getInstance().getGson().toJson(msg));
        }
    }

    public static void vFormat(String format, Object... msg) {
        if (mIsLogPrint) {
            Log.v(TAG, String.format(format, msg));
        }
    }

    public static void dFormat(String format, Object... msg) {
        if (mIsLogPrint) {
            Log.d(TAG, String.format(format, msg));
        }
    }

    public static void iFormat(String format, Object... msg) {
        if (mIsLogPrint) {
            Log.i(TAG, String.format(format, msg));
        }
    }

    public static void wFormat(String format, Object... msg) {
        if (mIsLogPrint) {
            Log.w(TAG, String.format(format, msg));
        }
    }

    public static void eFormat(String format, Object... msg) {
        if (mIsLogPrint) {
            Log.e(TAG, String.format(format, msg));
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> 写异常日志 同步 =================================================================

    /** 写异常日志 同步 默认已捕获 */
    public static void writeExceptionLog(Throwable throwable) {
        writeExceptionLog(true, throwable);
    }

    /** 写异常日志 同步 */
    public static void writeExceptionLog(boolean isCatch, Throwable throwable) {
        writeExceptionLog(isCatch, AFSF.PATH_FOLDER_LOG, AFSF.FILE_EXCEPTION_LOG, throwable, false);
    }

    /** 写异常日志 同步 */
    public static void writeExceptionLog(boolean isCatch, String path, String fileNameParam, Throwable throwableParam, boolean isSplicingDateParam) {
        if (!mIsLogWriteUpload) {
            return;
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwableParam.printStackTrace(printWriter);
        String logContent = (isCatch ? "[已捕获异常]\n" : "[未捕获异常]\n") + stringWriter.toString();
        writeLogFile(path, fileNameParam, logContent, isSplicingDateParam);
        addLogToDB(isCatch ? 2 : 3, logContent);
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> 写日志 同步 ====================================================================

    /** 写日志 同步 */
    public static void writeLog(String format, Object... msg) {
        writeLog(String.format(format, msg));
    }

    /** 写日志 同步 */
    public static void writeLog(String msg) {
        writeLog(AFSF.PATH_FOLDER_LOG, null, msg, true);
    }

    /** 写日志 同步 */
    public static void writeLog(String path, String fileNameParam, String msgParam, boolean isSplicingDateParam) {
        if (!mIsLogWriteUpload) {
            return;
        }
        writeLogFile(path, fileNameParam, msgParam, isSplicingDateParam);
        addLogToDB(1, msgParam);
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> 写日志 异步 ====================================================================

    /** 写日志 异步 */
    public static void writeLogAsync(String format, Object... msg) {
        writeLogAsync(String.format(format, msg));
    }

    /** 写日志 异步 */
    public static void writeLogAsync(String msg) {
        writeLogAsync(AFSF.PATH_FOLDER_LOG, null, msg, true);
    }

    /** 写日志 异步 */
    public static void writeLogAsync(String path, String fileNameParam, String msgParam, boolean isSplicingDateParam) {
        if (!mIsLogWriteUpload) {
            return;
        }
        new Thread(() -> {
            writeLogFile(path, fileNameParam, msgParam, isSplicingDateParam);
            addLogToDB(1, msgParam);
        }).start();
    }
    // </editor-fold> ==============================================================================


    /** 写日志到本地文件 */
    private static void writeLogFile(String path, String fileName, String msg, boolean isSplicingDateParam) {
        try {
            boolean isSplicingDate = TextUtils.isEmpty(fileName) || isSplicingDateParam;

            if (!TextUtils.isEmpty(fileName) && isSplicingDate) {
                fileName = fileName + AFSF.S_UNDERLINE + DateTimeUtils.getNowTime(AFSF.DT_003);
            } else if (TextUtils.isEmpty(fileName) && isSplicingDate) {
                fileName = DateTimeUtils.getNowTime(AFSF.DT_003);
            }

            File file = new File(path + AFSF.S_FS + fileName + AFSF.FILE_DT$LOG);
            msg = "【" + DateTimeUtils.getNowTime(AFSF.DT_001) + "】" + AFSF.S_LN + msg + AFSF.S_LN + AFSF.S_LN;
            FileUtils.writeStringToFile(file, msg, StandardCharsets.UTF_8.name(), true);
            d(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 添加日志数据到数据库 */
    public static void addLogToDB(int logType, String logContent) {
        if (logType == 3) { // 未拦截异常崩溃日志，程序已经崩溃所以不用启动上传Log服务
            mLogEntityDao.insert(getLogEntity(logType, logContent));
        }
        //TODO 暂时去除log日志上传
//        else {
//            RxUtils.asyncOperate(
//                    new RxUtils.TYObservableOnSubscribe<LogEntity>(null) {
//                        @Override
//                        public void onAsyncTask(ObservableEmitter<LogEntity> emitter) throws Exception {
//                            emitter.onNext(getLogEntity(logType, logContent));
//                        }
//                    },
//                    logEntity -> {
//                        mLogEntityDao.insert(logEntity);
//
//                        try {
//                            if (!HardwareUtils.getInstance().getNetworkState() || SystemUtils.checkServiceAlive(UploadLogService.class.getName())) {
//                                return;
//                            }
//                            if (AppManager.getAppManager().getCurrentActivity() != null || Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
//                                BaseApp.getI().startService(new Intent(BaseApp.getI(), UploadLogService.class));
//                            } else {// 兼容Android8.0启动服务方式
//                                BaseApp.getI().startForegroundService(new Intent(BaseApp.getI(), UploadLogService.class).putExtra(AFSF.KEY_IS_FOREGROUND_SERVICE, true));
//                            }
//                        } catch (Exception e) {
//                            L.writeExceptionLog(e);
//                        }
//                    }
//            );
//        }
    }

    /** 获取封装数据 */
    private static LogEntity getLogEntity(int logType, String logContent) {
        LogEntity logEntity = new LogEntity();
        logEntity.setKey_id(AFConfig.UPYA_KEY_ID);
        logEntity.setLog_type(logType);
        logEntity.setLog_content(logContent);
        logEntity.setLog_record_time(System.currentTimeMillis());
//        logEntity.setUser_key(TextUtils.isEmpty(mUserKey) ? getDeviceId() : mUserKey);
        logEntity.setUser_key(TextUtils.isEmpty(mUserKey) ? "" : mUserKey);
        logEntity.setSoftware_name(mSoftwareName);
        logEntity.setSoftware_type(1);
        logEntity.setSoftware_version_name(mVersionName);
        logEntity.setSoftware_version_code(mVersionCode);
        logEntity.setSoftware_package_name(AFConfig.APP_PACKAGE_NAME);
        logEntity.setSoftware_channel(AFConfig.CHANNEL);
        logEntity.setSystem_version_name(Build.VERSION.RELEASE);
        logEntity.setSystem_version_code(Build.VERSION.SDK_INT);
        logEntity.setDevice_id(getDeviceId());
        logEntity.setDevice_id("");
        logEntity.setDevice_brand(Build.BRAND);
        logEntity.setDevice_model(Build.MODEL);
        logEntity.setDevice_abis(AFSF.SUPPORTED_ABIS);
        logEntity.setDevice_screen_width(mDisplayMetrics.widthPixels);
        logEntity.setDevice_screen_height(mDisplayMetrics.heightPixels);
        return logEntity;
    }

    /** 尝试获取非空设备ID */
    private static String getDeviceId() {
        return TextUtils.isEmpty(mDeviceId) ? mDeviceId = SystemUtils.getDeviceId() : mDeviceId;
    }
}
