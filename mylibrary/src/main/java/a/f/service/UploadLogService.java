package a.f.service;

import android.content.Intent;
import android.text.TextUtils;

import com.jess.arms.base.BaseService;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import a.f.base.BaseApp;
import a.f.bean.common.secret.SecretBean;
import a.f.bean.dao.LogEntity;
import a.f.bean.dao.LogEntityDao;
import a.f.bean.response.BaseJson;
import a.f.utils.DateTimeUtils;
import a.f.utils.GsonUtils;
import a.f.utils.HardwareUtils;
import a.f.utils.L;
import a.f.utils.RxUtils;
import a.f.utils.SPUtils;
import a.f.utils.WidgetUtils;
import a.f.utils.constant.AFSF;
import a.f.utils.db.DBUtils;
import a.f.utils.secret.EncryptionUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * ================================================
 * 类名：a.f.service
 * 时间：2021/7/20 16:45
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class UploadLogService extends BaseService {

    public static final String SERVIDE_URL = AFSF.ULS_SERVIDE_URL;
    public static final String API_UPLOAD_LOG = AFSF.ULS_API_UPLOAD_LOG;
    public static final String ACTION_UPLOAD_LOG = AFSF.ULS_ACTION_UPLOAD_LOG;

    private final int LIMIT = 5; // 每次查询*条数据
    private final int MAX_ERROR_COUNT = 3; // 上传失败*次后停止上传
    private final String SP_DATE = getClass().getSimpleName();

    private UploadLogAPIService mUploadLogAPIService;
    private LogEntityDao mLogEntityDao;
    private boolean isTaskRunning; // 是否正在上传
    private int errorCount; // 上传失败计数，避免重复失败导致占用资源

    @Override
    public void init() {
        mUploadLogAPIService = BaseApp.getI().getAppComponent().repositoryManager().obtainRetrofitService(UploadLogAPIService.class);
        mLogEntityDao = DBUtils.getDaoSession().getLogEntityDao();
        isTaskRunning = false;
        errorCount = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent != null && intent.getBooleanExtra(AFSF.KEY_IS_FOREGROUND_SERVICE, false)) {
                startForeground((int) (System.currentTimeMillis() / 1000), WidgetUtils.createNullNotification());
            }
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }

        if (!isTaskRunning && HardwareUtils.getInstance().getNetworkState()) {
            isTaskRunning = true;
            queryNoUploadData();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /** 查询未上传的数据 */
    private void queryNoUploadData() {
        List<LogEntity> logEntityList = mLogEntityDao.queryBuilder().limit(LIMIT).list();
        if (errorCount >= MAX_ERROR_COUNT || logEntityList.isEmpty()) {
            isTaskRunning = false;
            errorCount = 0;
            stopSelf();
        } else {
            String dateLast = SPUtils.getInstance().getString(SP_DATE, SP_DATE); // 上一次执行日期
            String dateNow = DateTimeUtils.getNowTime(AFSF.DT_003); // 现在日期
            if (TextUtils.equals(dateLast, dateNow)) {
                uploadData(logEntityList);
            } else {
                SPUtils.getInstance().setString(SP_DATE, SP_DATE, dateNow);
                RxUtils.asyncOperate(
                        new RxUtils.TYObservableOnSubscribe<String>(AFSF.S_FS) {
                            @Override
                            public void onAsyncTask(ObservableEmitter<String> emitter) throws Exception {
                                clearLogFile();
                            }
                        },
                        s -> uploadData(logEntityList)
                );
            }
        }
    }

    /** 上传数据 */
    private void uploadData(List<LogEntity> logEntityList) {
        mUploadLogAPIService.uploadLog(EncryptionUtils.getInstance().encodeDataDouble(new SecretBean(ACTION_UPLOAD_LOG), GsonUtils.getInstance().getGson().toJson(logEntityList), AFSF.PUK, AFSF.IV))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxUtils.TYObserver<SecretBean>() {
                    @Override
                    public void onNext(SecretBean secretBean) {
                        String dataJson = EncryptionUtils.getInstance().decodeDataDouble(secretBean, AFSF.PRK, AFSF.IV);
                        BaseJson bj = GsonUtils.getInstance().fromJson(dataJson, BaseJson.class);
                        if (bj != null && bj.isSuccess(0)) {
                            mLogEntityDao.deleteInTx(logEntityList);
                        } else {
                            errorCount++;
                        }
                        queryNoUploadData();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        errorCount++;
                        queryNoUploadData();
                    }
                });
    }

    /** 清理日志文件 */
    private void clearLogFile() {
        try {
            File folderLog = new File(AFSF.PATH_FOLDER_LOG);
            if (!folderLog.exists()) {
                return;
            }
            if (folderLog.isFile()) {
                FileUtils.deleteQuietly(folderLog);
                return;
            }

            List<File> fileList = new ArrayList<>(FileUtils.listFiles(folderLog, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE));
            long sevenDaysAgoTime = DateTimeUtils.getTime(-AFSF.TIME_7D);
            for (File file : fileList) {
                try {
                    if (file.length() > (AFSF.DL_10MB) || file.lastModified() < sevenDaysAgoTime) {
                        FileUtils.deleteQuietly(file);
                    }
                } catch (Exception e) {
                    L.writeExceptionLog(e);
                }
            }
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

    /** 定义的 API 方法 */
    public interface UploadLogAPIService {
        /** 接口 */
        @POST(SERVIDE_URL + API_UPLOAD_LOG)
        Observable<SecretBean> uploadLog(@Body SecretBean secretBean);
    }

}
