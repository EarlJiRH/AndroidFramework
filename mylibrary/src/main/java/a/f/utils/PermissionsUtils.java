package a.f.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jess.arms.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;

import a.f.R;
import a.f.utils.constant.AFConfig;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:16
 * 描述：权限请求 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class PermissionsUtils {

    private final int REQUEST_CODE = 1024;
    private Activity mActivity;
    private IPermissionsCallback mIPermissionsCallback;
    private AlertDialog mDialogPermission;
    private List<String> rejectPermission; // 被拒绝的权限

    public PermissionsUtils(Activity activity, IPermissionsCallback iPermissionsCallback) {
        this.mActivity = Preconditions.checkNotNull(activity);
        this.mIPermissionsCallback = iPermissionsCallback;
    }

    /** 检查权限，并请求未授权权限 */
    public void checkPermissions(List<String> permissions) {
        List<String> unGranted = new ArrayList<>();
        for (String permission : permissions) {
            if (!checkPermissions(permission)) {
                unGranted.add(permission);
            }
        }

        if (unGranted.size() > 0) {
            requestPermissions(unGranted);
        } else {
            permissionsCallback(true);
        }
    }

    /** 检测权限请求结果，并弹窗请求未授权权限；在界面 onRequestPermissionsResult 方法调用此方法 */
    public void checkPermissionsRequest(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != REQUEST_CODE) {
            return;
        }

        rejectPermission = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                rejectPermission.add(permissions[i]);
            }
        }

        if (rejectPermission.size() > 0) { // 弹窗提示需要权限
            if (mDialogPermission != null && mDialogPermission.isShowing()) {
                mDialogPermission.dismiss();
            }
            mDialogPermission = new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.hint)
                    .setMessage(mActivity.getString(R.string.hintPermission) + "\n\n" + spliceNeedPermissions(rejectPermission))
                    .setNegativeButton(R.string.hintCancel, (dialog, which) -> permissionsCallback(false))
                    .setPositiveButton(R.string.hintGoSetting, (dialog, which) -> mActivity.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + AFConfig.APP_PACKAGE_NAME)), REQUEST_CODE))
                    .create();
            mDialogPermission.setCancelable(false);
            mDialogPermission.show();
        } else {
            permissionsCallback(true);
        }
    }

    /** 在界面 onActivityResult 方法调用此方法 */
    public void onActivityResult(int requestCode) {
        if (requestCode == REQUEST_CODE) {
            checkPermissions(rejectPermission);
        }
    }

    // =============================================================================================

    /** 检查某个权限，返回是否授权 */
    private boolean checkPermissions(String permissions) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            return ContextCompat.checkSelfPermission(mActivity, permissions) == PackageManager.PERMISSION_GRANTED;
        } else { // 如果SDK大于22，总是返回true，不执行动态授权
            return true;
        }
    }

    /** 请求权限 */
    private void requestPermissions(List<String> permissions) {
        ActivityCompat.requestPermissions(mActivity, permissions.toArray((new String[permissions.size()])), REQUEST_CODE);
    }

    /** 回调权限状态，回调一次后就不再持有界面对象，避免内存泄漏 */
    private void permissionsCallback(boolean permissionsState) {
        if (mIPermissionsCallback != null) {
            mIPermissionsCallback.permissionsCallback(permissionsState);
        }
        mActivity = null;
        mIPermissionsCallback = null;
        mDialogPermission = null;
    }

    /** 拼接所需权限文本 */
    private String spliceNeedPermissions(List<String> unGranted) {
        int size = unGranted.size();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(i + 1);
            stringBuilder.append("、");
            stringBuilder.append(translatePermission(unGranted.get(i)));
            if ((i + 1) < size) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    /** 转换/翻译 权限说明 */
    private String translatePermission(String permissionName) {
        String translatePermissionName = "";
        switch (permissionName) {

            case Manifest.permission.WRITE_CONTACTS:
                translatePermissionName = "编辑联系人";
                break;
            case Manifest.permission.GET_ACCOUNTS:
                translatePermissionName = "获取账户";
                break;
            case Manifest.permission.READ_CONTACTS:
                translatePermissionName = "读取联系人";
                break;

            case Manifest.permission.READ_CALL_LOG:
                translatePermissionName = "读取通话记录";
                break;
            case Manifest.permission.READ_PHONE_STATE:
                translatePermissionName = "读取设备信息";
                break;
            case Manifest.permission.CALL_PHONE:
                translatePermissionName = "拨打电话";
                break;
            case Manifest.permission.WRITE_CALL_LOG:
                translatePermissionName = "编辑通话日志";
                break;
            case Manifest.permission.USE_SIP:
                translatePermissionName = "使用SIP服务";
                break;
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                translatePermissionName = "过程输出调用";
                break;
            case Manifest.permission.ADD_VOICEMAIL:
                translatePermissionName = "添加语音信箱";
                break;

            case Manifest.permission.READ_CALENDAR:
                translatePermissionName = "读取日历";
                break;
            case Manifest.permission.WRITE_CALENDAR:
                translatePermissionName = "编辑日历";
                break;

            case Manifest.permission.CAMERA:
                translatePermissionName = "使用照相机";
                break;

            case Manifest.permission.BODY_SENSORS:
                translatePermissionName = "使用传感器";
                break;

            case Manifest.permission.ACCESS_FINE_LOCATION:
                translatePermissionName = "获取精确的位置信息";
                break;
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                translatePermissionName = "获取大概的位置信息";
                break;

            case Manifest.permission.READ_EXTERNAL_STORAGE:
                translatePermissionName = "读取存储空间";
                break;
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                translatePermissionName = "写入存储空间";
                break;

            case Manifest.permission.RECORD_AUDIO:
                translatePermissionName = "使用录音";
                break;

            case Manifest.permission.READ_SMS:
                translatePermissionName = "获取短信";
                break;
            case Manifest.permission.RECEIVE_WAP_PUSH:
                translatePermissionName = "接收WAP推送";
                break;
            case Manifest.permission.RECEIVE_MMS:
                translatePermissionName = "接收彩信";
                break;
            case Manifest.permission.RECEIVE_SMS:
                translatePermissionName = "接收短信";
                break;
            case Manifest.permission.SEND_SMS:
                translatePermissionName = "发送短信";
                break;
            default:
                break;
        }
        return translatePermissionName;
    }

    /** 权限回调 接口 */
    public interface IPermissionsCallback {
        /** 权限回调【permissionsState = 权限获取状态】 */
        void permissionsCallback(boolean permissionsState);
    }


/*

Dangerous Permissions 危险的权限
可以通过 adb shell pm list permissions -d -g 进行查看

group:android.permission-group.CONTACTS
  permission:android.permission.WRITE_CONTACTS
  permission:android.permission.GET_ACCOUNTS
  permission:android.permission.READ_CONTACTS

group:android.permission-group.PHONE
  permission:android.permission.READ_CALL_LOG
  permission:android.permission.READ_PHONE_STATE
  permission:android.permission.CALL_PHONE
  permission:android.permission.WRITE_CALL_LOG
  permission:android.permission.USE_SIP
  permission:android.permission.PROCESS_OUTGOING_CALLS
  permission:com.android.voicemail.permission.ADD_VOICEMAIL

group:android.permission-group.CALENDAR
  permission:android.permission.READ_CALENDAR
  permission:android.permission.WRITE_CALENDAR

group:android.permission-group.CAMERA
  permission:android.permission.CAMERA

group:android.permission-group.SENSORS
  permission:android.permission.BODY_SENSORS

group:android.permission-group.LOCATION
  permission:android.permission.ACCESS_FINE_LOCATION
  permission:android.permission.ACCESS_COARSE_LOCATION

group:android.permission-group.STORAGE
  permission:android.permission.READ_EXTERNAL_STORAGE
  permission:android.permission.WRITE_EXTERNAL_STORAGE

group:android.permission-group.MICROPHONE
  permission:android.permission.RECORD_AUDIO

group:android.permission-group.SMS
  permission:android.permission.READ_SMS
  permission:android.permission.RECEIVE_WAP_PUSH
  permission:android.permission.RECEIVE_MMS
  permission:android.permission.RECEIVE_SMS
  permission:android.permission.SEND_SMS
  permission:android.permission.READ_CELL_BROADCASTS

*/

}
