package a.f.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import a.f.base.BaseApp;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:23
 * 描述：硬件交互 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class HardwareUtils {


    private static volatile HardwareUtils mHardwareUtils; // 本类实例

    private HardwareUtils() {
    }

    /** 获取 HardwareUtils 的实例 */
    public static HardwareUtils getInstance() {
        if (mHardwareUtils == null) {
            synchronized (HardwareUtils.class) {
                if (mHardwareUtils == null) {
                    mHardwareUtils = new HardwareUtils();
                }
            }
        }
        return mHardwareUtils;
    }


    // <editor-fold> ===== 声音相关 ================================================================
    // 所需权限
    // <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    private MediaPlayer showSound_mediaPlayer; // showSound 方法的 mediaPlayer 实例

    /** show 声音 【resId = 声音资源】【isLooping = 是否无限循环】 */
    public void showSound(int resId, boolean isLooping) {
        stopSound();

        try {
            showSound_mediaPlayer = MediaPlayer.create(BaseApp.getI(), resId);
            showSound_mediaPlayer.start();
            showSound_mediaPlayer.setLooping(isLooping);
            showSound_mediaPlayer.setOnCompletionListener(mp -> {
                try {
                    stopSound();
                    L.d("结束播放");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** stop 声音 */
    public void stopSound() {
        try {
            if (showSound_mediaPlayer != null) {
                showSound_mediaPlayer.stop();
                showSound_mediaPlayer.release();
                showSound_mediaPlayer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 震动相关 ================================================================
    // 所需权限
    // <uses-permission android:name="android.permission.VIBRATE"/>
    private Vibrator showVibrator_vibrator; // showVibrator 方法的 vibrator 实例

    /** show 震动 【milliseconds = 震动时间 单位:毫秒】 */
    public void showVibrator(long milliseconds) {
        stopVibrator();

        try {
            showVibrator_vibrator = (Vibrator) BaseApp.getI().getSystemService(Context.VIBRATOR_SERVICE);
            if (showVibrator_vibrator != null && showVibrator_vibrator.hasVibrator()) {
                showVibrator_vibrator.vibrate(milliseconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** show 震动 【pattern = 震动时间 单位:毫秒】【repeat = -1:不重复；0:一直震动】 */
    public void showVibrator(long[] pattern, int repeat) {
        stopVibrator();

        try {
            showVibrator_vibrator = (Vibrator) BaseApp.getI().getSystemService(Context.VIBRATOR_SERVICE);
            if (showVibrator_vibrator != null && showVibrator_vibrator.hasVibrator()) {
                showVibrator_vibrator.vibrate(pattern, repeat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** stop 震动 */
    public void stopVibrator() {
        try {
            if (showVibrator_vibrator != null) {
                showVibrator_vibrator.cancel();
                showVibrator_vibrator = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 闪光灯相关 ==============================================================
    // 所需权限
    // <uses-permission android:name="android.permission.CAMERA"/>
    private Thread showFlashlight_thread; // showFlashlight 方法的 thread 实例
    private CameraManager showFlashlight_cameraManager; // showFlashlight 方法的 cameraManager 实例
    private List<String> showFlashlight_cameraIDs; // showFlashlight 方法的 cameraIDs 实例

    /** show 闪光灯 【count = 闪烁次数（count < 0：无限闪烁）】【intervalTimeONToOFF = 亮时长 单位:毫秒】【intervalTimeOFFToON = 灭时长 单位:毫秒】 */
    public void showFlashlight(long count, long intervalTimeONToOFF, long intervalTimeOFFToON) {
        stopFlashlight();

        try {
            showFlashlight_cameraManager = (CameraManager) BaseApp.getI().getSystemService(Context.CAMERA_SERVICE);
            if (showFlashlight_cameraIDs == null) {
                showFlashlight_cameraIDs = new ArrayList<>();
            }
            if (showFlashlight_cameraIDs.isEmpty()) {
                for (String id : showFlashlight_cameraManager.getCameraIdList()) {
                    Boolean flashAvailable = showFlashlight_cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    if (flashAvailable != null && flashAvailable) {
                        showFlashlight_cameraIDs.add(id);
                    }
                }
            }
            if (showFlashlight_cameraIDs.isEmpty()) {
                return;
            }

            showFlashlight_thread = new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                    try {
                        if (count < 0) {
                            while (showFlashlight_cameraManager != null && !showFlashlight_thread.isInterrupted()) {
                                startFlashlight(intervalTimeONToOFF, intervalTimeOFFToON);
                            }
                        } else {
                            for (long i = 0; i < count && showFlashlight_cameraManager != null && !showFlashlight_thread.isInterrupted(); i++) {
                                startFlashlight(intervalTimeONToOFF, intervalTimeOFFToON);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        stopFlashlight();
                    }
                }

                /** 开始闪烁 */
                @RequiresApi(api = Build.VERSION_CODES.M)
                private void startFlashlight(long intervalTimeONToOFF, long intervalTimeOFFToON) {
                    try {
                        for (String id : showFlashlight_cameraIDs) {
                            showFlashlight_cameraManager.setTorchMode(id, true);
                        }
                        SystemClock.sleep(intervalTimeONToOFF);
                        for (String id : showFlashlight_cameraIDs) {
                            showFlashlight_cameraManager.setTorchMode(id, false);
                        }
                        SystemClock.sleep(intervalTimeOFFToON);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            showFlashlight_thread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** stop 闪光灯 */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void stopFlashlight() {
        try {
            if (showFlashlight_thread != null) {
                showFlashlight_thread.interrupt();
                showFlashlight_thread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (showFlashlight_cameraManager != null) {
                for (String id : showFlashlight_cameraIDs) {
                    showFlashlight_cameraManager.setTorchMode(id, false);
                }
                showFlashlight_cameraManager = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 屏幕相关 ================================================================
    // 所需权限
    // <uses-permission android:name="android.permission.WAKE_LOCK"/>
    private PowerManager showLightScreen_powerManager; // showLightScreen 方法的 powerManager 实例
    private PowerManager.WakeLock showLightScreen_wakeLock; // showLightScreen 方法的 wakeLock 实例

    /** 屏幕常亮/点亮 */
    public void showLightScreen() {
        releaseScreen();

        try {
            showLightScreen_powerManager = (PowerManager) BaseApp.getI().getSystemService(Context.POWER_SERVICE);
            showLightScreen_wakeLock = showLightScreen_powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, L.getTag());
            showLightScreen_wakeLock.acquire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 释放 屏幕常亮/点亮 */
    public void releaseScreen() {
        try {
            showLightScreen_powerManager = null;
            if (showLightScreen_wakeLock != null) {
                showLightScreen_wakeLock.release();
            }
            showLightScreen_wakeLock = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 解锁屏幕相关 =============================================================
    // 所需权限
    // <uses-permission android:name="android.permission.DISABLE_KEYGUARD"/>
    private KeyguardManager unlockScreen_keyguardManager; // unlockScreen 方法的 keyguardManager 对象
    private KeyguardManager.KeyguardLock unlockScreen_keyguardLock; // unlockScreen 方法的 keyguardLock 对象

    /** 解锁屏幕 */
    public void unlockScreen() {
        recoverScreen();

        try {
            if (unlockScreen_keyguardManager == null || unlockScreen_keyguardLock == null) {
                unlockScreen_keyguardManager = (KeyguardManager) BaseApp.getI().getSystemService(Context.KEYGUARD_SERVICE);
                unlockScreen_keyguardLock = unlockScreen_keyguardManager.newKeyguardLock(L.getTag());
                unlockScreen_keyguardLock.disableKeyguard();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 恢复可锁屏幕 */
    public void recoverScreen() {
        try {
            unlockScreen_keyguardManager = null;
            if (unlockScreen_keyguardLock != null) {
                unlockScreen_keyguardLock.reenableKeyguard();
            }
            unlockScreen_keyguardLock = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 判断屏幕状态【黑屏&未解锁 = true】【解锁 = false】 */
    public boolean inputMode() {
        boolean inputMode = true;
        try {
            KeyguardManager keyguardManager = (KeyguardManager) BaseApp.getI().getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                inputMode = keyguardManager.inKeyguardRestrictedInputMode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputMode;
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 音频相关 ================================================================
    // 无所需权限

    /** 获取已设置音量 【streamType ||= AudioManager.STREAM_MUSIC】 */
    public int getCurrentVolume(int streamType) {
        try {
            AudioManager audioManager = (AudioManager) BaseApp.getI().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                return audioManager.getStreamVolume(streamType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** 获取总音量 【streamType ||= AudioManager.STREAM_MUSIC】 */
    public int getTotalVolume(int streamType) {
        try {
            AudioManager audioManager = (AudioManager) BaseApp.getI().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                return audioManager.getStreamMaxVolume(streamType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** 调节音量 【index = 音量大小】【streamType ||= AudioManager.STREAM_MUSIC】 */
    public void adjustVolume(int index, int streamType) {
        try {
            AudioManager audioManager = (AudioManager) BaseApp.getI().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager == null) {
                return;
            }

            int maxVolume = audioManager.getStreamMaxVolume(streamType);
            if (index < 0) {
                index = 0;
            } else if (index > maxVolume) {
                index = maxVolume;
            }
            audioManager.setStreamVolume(streamType, index, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 网络相关 ================================================================
    // 所需权限
    // <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

    /**
     * 获取网络状态
     *
     * @return 返回是否连接网络
     */
    public boolean getNetworkState() {
        return getNetworkState(-1);
    }

    /**
     * 获取网络状态
     *
     * @param connectivityManagerType 网络类型（例如：ConnectivityManager.TYPE_WIFI）, 不需要指定匹配类型则传-1
     * @return 是否与指定网络类型相匹配
     */
    public boolean getNetworkState(int connectivityManagerType) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) BaseApp.getI().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }

            Network[] networks = connectivityManager.getAllNetworks();
            for (Network mNetwork : networks) {
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (connectivityManagerType == -1) {
                    if (networkInfo.isConnected()) {
                        return true;
                    }
                } else {
                    if (networkInfo.getType() == connectivityManagerType) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== WIFI相关 ================================================================
    // 所需权限
    // <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    // <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>

    /** 设置 WiFi 状态 */
    public void setWifiEnabled(boolean isEnable) {
        try {
            WifiManager mWifiManager = (WifiManager) BaseApp.getI().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (mWifiManager == null) {
                return;
            }

            if (isEnable && !mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(true);
            } else if (!isEnable && mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 屏幕亮度相关 =============================================================
    private Thread showScreenTwinkle_thread; // showScreenTwinkle 方法的 thread 对象
    private volatile float screenBrightnessOriginal = -1; // 原始亮度

    /** 获取屏幕亮度 */
    public synchronized float getScreenBrightness(Activity activity) {
        try {
            float screenBrightnessTemp = activity.getWindow().getAttributes().screenBrightness;
            if (screenBrightnessTemp == -1) {
                return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS) / 255f;
            } else {
                return screenBrightnessTemp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.5f; // 默认返回0.5
    }

    /** 设置屏幕亮度；取值范围0.0~1.0 */
    public synchronized void setScreenBrightness(Activity activity, float brightness) {
        if (brightness < 0) {
            brightness = 0;
        }
        if (brightness > 1) {
            brightness = 1;
        }

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = brightness;
        activity.getWindow().setAttributes(lp);
    }

    /** 显示屏幕闪烁 */
    public void showScreenTwinkle(Activity activity, long count, long intervalTimeONToOFF, long intervalTimeOFFToON) {
        if (screenBrightnessOriginal != -1) {
            return;
        }

        stopScreenTwinkle();
        if (count > -1) {
            screenBrightnessOriginal = getScreenBrightness(activity);
        }
        showScreenTwinkle_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (count < 0) {
                        while (showScreenTwinkle_thread != null) {
                            startScreenTwinkle(intervalTimeONToOFF, intervalTimeOFFToON);
                        }
                    } else {
                        for (long i = 0; i < count && showScreenTwinkle_thread != null; i++) {
                            startScreenTwinkle(intervalTimeONToOFF, intervalTimeOFFToON);
                        }
                        activity.runOnUiThread(() -> setScreenBrightness(activity, screenBrightnessOriginal));
                        SystemClock.sleep(100);
                    }

                    stopScreenTwinkle();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /** 开始闪烁 */
            private void startScreenTwinkle(long intervalTimeONToOFF, long intervalTimeOFFToON) throws Exception {
                activity.runOnUiThread(() -> setScreenBrightness(activity, 1));
                SystemClock.sleep(intervalTimeONToOFF);
                activity.runOnUiThread(() -> setScreenBrightness(activity, 0));
                SystemClock.sleep(intervalTimeOFFToON);
            }
        });
        showScreenTwinkle_thread.start();
    }

    /** 停止屏幕闪烁 */
    public void stopScreenTwinkle() {
        try {
            screenBrightnessOriginal = -1;
            if (showScreenTwinkle_thread != null) {
                showScreenTwinkle_thread.interrupt();
                showScreenTwinkle_thread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // </editor-fold> ==============================================================================

}
