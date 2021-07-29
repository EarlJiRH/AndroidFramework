package a.f.service;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseService;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import a.f.R;
import a.f.R2;
import a.f.base.BaseApp;
import a.f.base.BaseBean;
import a.f.base.BaseDialog;
import a.f.bean.common.secret.SecretBean;
import a.f.bean.response.BaseJson;
import a.f.utils.AFVariableUtils;
import a.f.utils.DateTimeUtils;
import a.f.utils.GsonUtils;
import a.f.utils.L;
import a.f.utils.RxUtils;
import a.f.utils.SPUtils;
import a.f.utils.SystemUtils;
import a.f.utils.ToastUtils;
import a.f.utils.WidgetUtils;
import a.f.utils.callback.CallBack;
import a.f.utils.callback.CallBackValue;
import a.f.utils.constant.AFConfig;
import a.f.utils.constant.AFSF;
import a.f.utils.secret.EncryptionUtils;
import a.f.widget.customtextview.MarqueeTextView;
import a.f.widget.customtextview.ScrollTextView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.progressmanager.body.ProgressInfo;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * ================================================
 * 类名：a.f.service
 * 时间：2021/7/20 16:44
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class CheckUpdateService extends BaseService {

    public static final String SERVIDE_URL = AFSF.CUS_SERVIDE_URL;
    public static final String API_UPDATE = AFSF.CUS_API_UPDATE;
    public static final String ACTION_UPDATE = AFSF.CUS_ACTION_UPDATE;

    public static final String CU_IS_AUTO = "isAuto"; // 是否自动安装更新，适用于工控机等已Root的设备
    public static final String CU_IS_MANUAL = "isManual"; // 是否手动点击更新
    public static final String CU_IS_KILL_APP = "isKillApp"; // 取消或安装前是否杀死App
    public static final String CU_STRATEGYBEAN = "StrategyBean"; // 自动检查更新策略，如果是手动点击更新则策略不生效
    public static final String CU_HINT_STARTCHECKUPDATEING = "开始检查更新";
    public static final String CU_HINT_CHECKUPDATEING = "正在检查更新中";
    public static final String CU_HINT_CUFAILED = "检查更新失败：";
    public static final String CU_HINT_DATAPARSINGFAILED = "检查更新返回数据解析失败";
    public static final String CU_HINT_UPDATE_TITLE = "App升级 v";
    public static final String CU_HINT_NOW_UPDATE = "立即升级";
    public static final String CU_HINT_CANCEL = "取消";
    public static final String CU_HINT_DOWNLOADAPP = "正在下载App";
    public static final String CU_HINT_PROGRESS = "%";
    public static final String CU_HINT_PROGRESS0 = "0%";
    public static final String CU_HINT_ERROR_TITLE = "错误提示";
    public static final String CU_HINT_DOWNLO_ERROR = "下载失败，程序遇到错误：";
    public static final String CU_HINT_RETRY = "立即重试";
    public static final String CU_HINT_INSTALL_PERMISSIONS = "为了方便App升级，请手动打开允许来自此来源的应用。";

    public static CallBackValue CALLBACKVALUE_INSTALLAPK; // 回调 安装应用（适用于工控机等已Root的设备 静默安装应用操作）
    private boolean mTasking; // 是否正在检查更新
    private boolean mIsAuto; // 是否自动安装更新，适用于工控机等已Root的设备
    private boolean mIsManual; // 是否手动点击更新
    private boolean mIsKillApp; // 取消或安装前是否杀死App
    private StrategyBean mStrategyBean; // 自动检查更新策略，如果是手动点击更新则策略不生效
    private CheckUpdateAPIService mCheckUpdateAPIService;
    private CheckUpdateDialog mCheckUpdateDialog;
    private Disposable mDisposable;

    @Override
    public void init() {
        mTasking = false;
        mCheckUpdateAPIService = BaseApp.getI().getAppComponent().repositoryManager().obtainRetrofitService(CheckUpdateAPIService.class);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mTasking) {
            ToastUtils.getInstance().showToast(CU_HINT_CHECKUPDATEING, false);
        } else {
            mTasking = true;
            mIsAuto = intent != null && intent.getBooleanExtra(CU_IS_AUTO, false);
            mIsManual = intent != null && intent.getBooleanExtra(CU_IS_MANUAL, false);
            mIsKillApp = intent != null && intent.getBooleanExtra(CU_IS_KILL_APP, true);
            mStrategyBean = intent == null ? null : (StrategyBean) intent.getSerializableExtra(CU_STRATEGYBEAN);
            if (checkStrategy(null)) {
                checkUpdate();
            } else {
                finishTask();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 检查更新
     */
    private void checkUpdate() {
        if (mIsManual) {
            ToastUtils.getInstance().showToast(CU_HINT_STARTCHECKUPDATEING);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("appKey", AFConfig.UPYA_KEY_ID);
        params.put("versionCode", SystemUtils.getVersionCode());
        params.put("channel", AFConfig.CHANNEL);

        mCheckUpdateAPIService.appUpdate(EncryptionUtils.getInstance().encodeDataDouble(new SecretBean(ACTION_UPDATE), GsonUtils.getInstance().getGson().toJson(params), AFSF.PUK, AFSF.IV))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxUtils.TYObserver<SecretBean>() {
                    @Override
                    public void onNext(SecretBean sb) {
                        String dataJson = EncryptionUtils.getInstance().decodeDataDouble(sb, AFSF.PRK, AFSF.IV);
                        BaseJson<VersionResultBean> bj = GsonUtils.getInstance().fromJsonToBaseJson(dataJson, new TypeToken<BaseJson<VersionResultBean>>() {
                        });
                        if (bj == null) {
                            showHint(CU_HINT_DATAPARSINGFAILED);
                            finishTask();
                        } else if (bj.isSuccess(0)) {
                            VersionResultBean vrBean = bj.getData();
                            if (vrBean.getUpdate()) {
                                if (mIsAuto) {
                                    downloadInstall(mIsAuto, mIsManual, vrBean.getUrl());
                                } else if (checkStrategy(vrBean.getVersionName())) {
                                    showDialog(vrBean);
                                } else {
                                    finishTask();
                                }
                            } else {
                                showHint(bj.getMsg());
                                finishTask();
                            }
                        } else {
                            showHint(bj.getMsg());
                            finishTask();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showHint(CU_HINT_CUFAILED + e.getMessage());
                        finishTask();
                    }

                    /** 显示弹窗 */
                    private void showDialog(VersionResultBean vrBean) {
                        Activity activity = AppManager.getAppManager().getTopActivity();
                        if (activity == null) {
                            finishTask();
                            return;
                        }

                        WidgetUtils.rapidDismissDialog(mCheckUpdateDialog);
                        mCheckUpdateDialog = new CheckUpdateDialog(activity, vrBean);
                        mCheckUpdateDialog.show();
                    }
                });
    }

    /**
     * 下载安装
     */
    private void downloadInstall(boolean isAuto, boolean isManual, String fileUrl) {
        if (!isAuto) {
            ProgressManager.getInstance().addResponseListener(fileUrl, new ProgressListener() {
                @Override
                public void onProgress(ProgressInfo pif) {
                    if (mCheckUpdateDialog != null && mCheckUpdateDialog.isShowing()) {
                        mCheckUpdateDialog.setProgress(pif.getPercent());
                    }
                }

                @Override
                public void onError(long id, Exception e) {
                }
            });
        }
        mDisposable = mCheckUpdateAPIService
                .downloadFile(fileUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(responseBody -> {
                            try {
                                File fileApp = new File(AFSF.PATH_FILE_APP$APK);
                                FileUtils.deleteQuietly(fileApp);

                                InputStream is = responseBody.byteStream();
                                byte[] readBytes = new byte[1024 * 1024 * 8];
                                int readSize;
                                while ((readSize = is.read(readBytes)) != -1) {
                                    FileUtils.writeByteArrayToFile(fileApp, readBytes, 0, readSize, true);
                                }
                                IOUtils.closeQuietly(is);

                                L.writeLog("CheckUpdateService == 下载App成功，开始安装 apkUrl:" + fileUrl);
                                if (isAuto) {
                                    if (CALLBACKVALUE_INSTALLAPK != null) {
                                        CALLBACKVALUE_INSTALLAPK.onBack(AFVariableUtils.getListForAllType(fileApp.getAbsolutePath()));
                                    }
                                } else {
                                    SystemUtils.installApk(fileApp.getAbsolutePath());
                                    if (mIsKillApp) {
                                        ArmsUtils.killAll();
                                    }
                                }
                                BaseApp.getI().mHandler.post(() -> finishTask());
                            } catch (Exception e) {
                                if (isAuto) {
                                    BaseApp.getI().mHandler.post(() -> finishTask());
                                } else if (mTasking) {
                                    mCheckUpdateDialog.hintFailed(e.getMessage());
                                }
                            }
                        },
                        throwable -> {
                            if (isAuto) {
                                BaseApp.getI().mHandler.post(() -> finishTask());
                            } else if (mTasking) {
                                mCheckUpdateDialog.hintFailed(throwable.getMessage());
                            }
                        });
    }

    /**
     * 检查策略
     *
     * @return 是否继续，否则停止
     */
    private boolean checkStrategy(String version) {
        if (mIsAuto || mIsManual || mStrategyBean == null) {
            return true;
        }

        switch (mStrategyBean.strategy) {
            case StrategyBean.STRATEGY_ONCE_A_DAY: {
                String cud = SPUtils.getInstance().getString(AFSF.SP_NAME_CHECK_UPDATE, AFSF.SP_KEY_CHECK_UPDATE_DATE);
                String today = DateTimeUtils.getNowTime(AFSF.DT_003);
                // 检测到日期不一致则检查更新，在用户取消时储存检查更新日期
                if (!TextUtils.equals(cud, today)) {
                    return true;
                } else {
                    return false;
                }
            }
            case StrategyBean.STRATEGY_EVERY_N_DAYS: {
                String cud = SPUtils.getInstance().getString(AFSF.SP_NAME_CHECK_UPDATE, AFSF.SP_KEY_CHECK_UPDATE_DATE);
                if (TextUtils.isEmpty(cud)) {
                    // 如果为空表示没有上一次检查更新时间则检查更新，在用户取消时储存检查更新日期
                    return true;
                } else {
                    long cudTime = DateTimeUtils.getDateToTimestamp(AFSF.DT_003, cud);
                    long todayTime = DateTimeUtils.getNowTime();
                    // 如果今天减上一次检查更新时间大于N天则检查更新，在用户取消时储存检查更新日期
                    if ((todayTime - cudTime) >= (mStrategyBean.skipDay * AFSF.TIME_1D)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            case StrategyBean.STRATEGY_SKIP_CURRENT_VERSION: {
                if (TextUtils.isEmpty(version)) {
                    return true;
                }
                String cuv = SPUtils.getInstance().getString(AFSF.SP_NAME_CHECK_UPDATE, AFSF.SP_KEY_CHECK_UPDATE_VERSION);
                if (TextUtils.equals(cuv, version)) {
                    return false;
                } else {
                    return true;
                }
            }
            default:
                return true;
        }

    }

    /**
     * 显示提示信息
     */
    private void showHint(String msg) {
        if (mIsManual) {
            ToastUtils.getInstance().showToast(msg, false);
        }
    }

    /**
     * 用户点击取消更新
     */
    private void cancelUpdate(VersionResultBean bean) {
        SPUtils.getInstance().setString(AFSF.SP_NAME_CHECK_UPDATE, AFSF.SP_KEY_CHECK_UPDATE_DATE, DateTimeUtils.getNowTime(AFSF.DT_003));
        SPUtils.getInstance().setString(AFSF.SP_NAME_CHECK_UPDATE, AFSF.SP_KEY_CHECK_UPDATE_VERSION, bean.getVersionName());
    }

    /**
     * 结束任务，停止服务
     */
    private void finishTask() {
        mTasking = false;
        WidgetUtils.rapidDismissDialog(mCheckUpdateDialog);
        stopSelf();
    }

    // =============================================================================================

    /**
     * 检查更新 弹窗
     */
    public class CheckUpdateDialog extends BaseDialog {

        @BindView(R2.id.dialogCuTitle)
        MarqueeTextView dialogCuTitle;
        @BindView(R2.id.dialogCuContent)
        ScrollTextView dialogCuContent;
        @BindView(R2.id.dialogCuProgressBar)
        ProgressBar dialogCuProgressBar;
        @BindView(R2.id.dialogCuProgressText)
        TextView dialogCuProgressText;
        @BindView(R2.id.dialogCuProgressLayout)
        LinearLayout dialogCuProgressLayout;
        @BindView(R2.id.dialogCuBtn1)
        TextView dialogCuBtn1;
        @BindView(R2.id.dialogCuBtnLine)
        View dialogCuBtnLine;
        @BindView(R2.id.dialogCuBtn2)
        TextView dialogCuBtn2;

        private int mState;
        private VersionResultBean mVRBean;

        private CheckUpdateDialog(Activity activity, VersionResultBean vrBean) {
            super(activity, R.layout.dialog_check_update);
            setCancelable(false);
            mVRBean = vrBean;
            setState(1, null);
        }

        @OnClick({R2.id.dialogCuBtn1, R2.id.dialogCuBtn2})
        public void onViewClicked(View view) {
            if (view.getId() == R.id.dialogCuBtn1) {
                switch (mState) {
                    case 1: // 提示升级 取消
                        cancelUpdate(mVRBean);
                        finishTask();
                        break;
                    case 2: // 下载App 取消
                    case 3: // 错误提示 取消
                        if (mVRBean.getForced()) { // 退出App
                            finishTask();
                            if (mIsKillApp) {
                                ArmsUtils.exitApp();
                            }
                        } else { // 取消下载任务
                            finishTask();
                            if (mDisposable != null && !mDisposable.isDisposed()) {
                                mDisposable.dispose();
                            }
                        }
                        break;
                    default:
                        break;
                }
            } else if (view.getId() == R.id.dialogCuBtn2) {
                switch (mState) {
                    case 1: // 立即升级
                    case 3: // 错误提示 立即重试
                        setState(2, null);
                        downloadInstall(false, false, mVRBean.getUrl());
                        break;
                    default:
                        break;
                }
            }
        }

        /**
         * 设置状态
         *
         * @param state 布局状态
         */
        private void setState(int state, String msg) {
            mState = state;
            switch (mState) {
                case 1:
                    // 提示升级
                    dialogCuTitle.setText(CU_HINT_UPDATE_TITLE + mVRBean.getVersionName());
                    dialogCuContent.setVisibility(View.VISIBLE);
                    dialogCuContent.setText(mVRBean.getUpdateDescribe());
                    dialogCuProgressLayout.setVisibility(View.GONE);
                    dialogCuBtn2.setText(CU_HINT_NOW_UPDATE);
                    dialogCuBtn2.setVisibility(View.VISIBLE);
                    if (mVRBean.getForced()) {
                        dialogCuBtnLine.setVisibility(View.GONE);
                        dialogCuBtn1.setVisibility(View.GONE);
                    } else {
                        dialogCuBtnLine.setVisibility(View.VISIBLE);
                        dialogCuBtn1.setVisibility(View.VISIBLE);
                        dialogCuBtn1.setText(CU_HINT_CANCEL);
                    }
                    break;
                case 2:
                    // 下载App中
                    dialogCuTitle.setText(CU_HINT_DOWNLOADAPP);
                    dialogCuContent.setVisibility(View.GONE);
                    dialogCuProgressLayout.setVisibility(View.VISIBLE);
                    dialogCuProgressBar.setProgress(0);
                    dialogCuProgressText.setText(CU_HINT_PROGRESS0);
                    dialogCuBtn1.setVisibility(View.VISIBLE);
                    dialogCuBtn1.setText(CU_HINT_CANCEL);
                    dialogCuBtn2.setVisibility(View.GONE);
                    break;
                case 3:
                    // 错误提示
                    dialogCuTitle.setText(CU_HINT_ERROR_TITLE);
                    dialogCuContent.setVisibility(View.VISIBLE);
                    dialogCuContent.setText(CU_HINT_DOWNLO_ERROR + msg);
                    dialogCuProgressLayout.setVisibility(View.GONE);
                    dialogCuBtn2.setText(CU_HINT_RETRY);
                    dialogCuBtn2.setVisibility(View.VISIBLE);
                    if (mVRBean.getForced()) {
                        dialogCuBtnLine.setVisibility(View.GONE);
                        dialogCuBtn1.setVisibility(View.GONE);
                    } else {
                        dialogCuBtnLine.setVisibility(View.VISIBLE);
                        dialogCuBtn1.setVisibility(View.VISIBLE);
                        dialogCuBtn1.setText(CU_HINT_CANCEL);
                    }
                    break;

                default:
                    break;
            }
        }

        /**
         * 设置进度
         */
        private void setProgress(int progress) {
            if (dialogCuProgressBar == null) {
                return;
            }
            dialogCuProgressBar.setProgress(progress);
            dialogCuProgressText.setText(progress + CU_HINT_PROGRESS);
        }

        /**
         * 提示失败
         */
        private void hintFailed(String msg) {
            BaseApp.getI().mHandler.post(() -> setState(3, msg));
        }

    }

    // =============================================================================================

    /**
     * 检查安装权限类
     */
    public static class CheckInstallPermissions {
        private static final int REQUEST_CODE = 10246;
        private static CallBack mCallBack;
        private static AlertDialog mAlertDialogRequestInstall;

        public static void setCallBack(CallBack callBack) {
            mCallBack = callBack;
        }

        public static void onActivityResult(Activity activity, int requestCode) {
            if (requestCode == REQUEST_CODE) {
                checkInstallPermissions(activity);
            }
        }

        /**
         * 检查安装权限
         */
        public static void checkInstallPermissions(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !activity.getPackageManager().canRequestPackageInstalls()) {
                //权限没有打开则提示用户去手动打开
                WidgetUtils.rapidDismissDialog(mAlertDialogRequestInstall);
                mAlertDialogRequestInstall = new AlertDialog.Builder(activity)
                        .setTitle(R.string.hint)
                        .setMessage(CU_HINT_INSTALL_PERMISSIONS)
                        .setNegativeButton(R.string.hintCancel, null)
                        .setPositiveButton(R.string.hintConfirm, (dialog, which) -> WidgetUtils.startActivityForResult(activity,
                                new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName())),
                                REQUEST_CODE,
                                false))
                        .create();
                mAlertDialogRequestInstall.setCancelable(false);
                mAlertDialogRequestInstall.show();
            } else {
                try {
                    if (mCallBack != null) {
                        mCallBack.onBack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mCallBack = null;
                    WidgetUtils.rapidDismissDialog(mAlertDialogRequestInstall);
                    mAlertDialogRequestInstall = null;
                }
            }
        }
    }

    // =============================================================================================

    /**
     * 检查更新策略 Bean
     */
    public static class StrategyBean extends BaseBean {
        public static final int STRATEGY_ONCE_A_DAY = 1; // 每天仅1次
        public static final int STRATEGY_EVERY_N_DAYS = 2; // 每N天仅1次
        public static final int STRATEGY_SKIP_CURRENT_VERSION = 3; // 跳过当前版本

        private int strategy; // 策略
        private int skipDay; // 跳过的天数 适用于 STRATEGY_EVERY_N_DAYS

        public StrategyBean(int strategy, int skipDay) {
            this.strategy = strategy;
            this.skipDay = skipDay;
        }

        public static StrategyBean newInstance(int strategy) {
            return newInstance(strategy, 0);
        }

        public static StrategyBean newInstance(int strategy, int arg1) {
            switch (strategy) {
                case STRATEGY_ONCE_A_DAY:
                    return new StrategyBean(strategy, 0);
                case STRATEGY_EVERY_N_DAYS:
                    return new StrategyBean(strategy, arg1);
                case STRATEGY_SKIP_CURRENT_VERSION:
                    return new StrategyBean(strategy, 0);
                default:
                    return new StrategyBean(strategy, 0);
            }
        }
    }

    // =============================================================================================

    /**
     * 版本结果 Bean
     */
    public static class VersionResultBean extends BaseBean {
        private Boolean isUpdate;
        private String versionName;
        private Boolean isForced;
        private String updateDescribe;
        private String url;

        public Boolean getUpdate() {
            return isUpdate;
        }

        public void setUpdate(Boolean update) {
            isUpdate = update;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public Boolean getForced() {
            return isForced;
        }

        public void setForced(Boolean forced) {
            isForced = forced;
        }

        public String getUpdateDescribe() {
            return updateDescribe;
        }

        public void setUpdateDescribe(String updateDescribe) {
            this.updateDescribe = updateDescribe;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    // =============================================================================================

    /**
     * 定义的 API 方法
     */
    public interface CheckUpdateAPIService {
        /**
         * 接口
         */
        @POST(SERVIDE_URL + API_UPDATE)
        Observable<SecretBean> appUpdate(@Body SecretBean secretBean);

        /**
         * 下载文件
         */
        @Streaming
        @GET
        Observable<ResponseBody> downloadFile(@Url String fileUrl);
    }
}
