package a.f.utils.serialport;

import android.os.Handler;

/**
 * ================================================
 * 类名：a.f.utils.serialport
 * 时间：2021/7/20 17:33
 * 描述：串口通讯 基类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public abstract class BaseSerialPortCommTemplate implements Handler.Callback {

    //    private final int WHAT_RECEIVE = 101;
    //    private final int WHAT_WRITE = 102;
    //    private final String KEY_DATA_RECEIVE = "receive";
    //    private final String KEY_DATA_WRITE = "write";
    //
    //    private Handler mHandler; // 主线程消息队列
    //    private Handler mHandlerForWrite; // 写数据消息队列
    //    private boolean isConnected; // 是否连接
    //    private SerialPort mSerialPort; // 串口工具
    //    private OutputStream mOutputStream; // 写数据输出流
    //    private SerialReadThread mSerialReadThread; // 读串口线程
    //
    //    protected BaseSerialPortComm() {
    //        mHandler = new Handler(Looper.getMainLooper(), this);
    //        HandlerThread handlerThread = new HandlerThread("BaseSerialPortComm-Write"); // 写数据Handler线程
    //        handlerThread.start();
    //        mHandlerForWrite = new Handler(handlerThread.getLooper(), this);
    //    }
    //
    //    /** 是否已连接 */
    //    public boolean isConnected() {
    //        return isConnected;
    //    }
    //
    //    /** 连接设备 */
    //    public void connectDevice(String devicePath, int deviceBaudRate) {
    //        if (isConnected()) { // 如果之前有连接，则强制断开
    //            disconnectDevice();
    //            mHandler.postDelayed(() -> startConnectDevice(new SPDeviceBean(devicePath, deviceBaudRate)), 100);
    //        } else {
    //            startConnectDevice(new SPDeviceBean(devicePath, deviceBaudRate));
    //        }
    //    }
    //
    //    /** 断开设备 */
    //    public void disconnectDevice() {
    //        disconnectDevice(false);
    //    }
    //
    //    /**
    //     * 断开设备
    //     *
    //     * @param isCallBackConnectFailed 是否回调连接失败
    //     */
    //    public void disconnectDevice(boolean isCallBackConnectFailed) {
    //        isConnected = false;
    //        try {
    //            if (mSerialReadThread != null)
    //                mSerialReadThread.close();
    //        } catch (Exception e) {
    //            L.writeExceptionLog(e);
    //        } finally {
    //            mSerialReadThread = null;
    //        }
    //        try {
    //            if (mOutputStream != null)
    //                mOutputStream.close();
    //        } catch (Exception e) {
    //            L.writeExceptionLog(e);
    //        } finally {
    //            mOutputStream = null;
    //        }
    //        try {
    //            if (mSerialPort != null)
    //                mSerialPort.close();
    //        } catch (Exception e) {
    //            L.writeExceptionLog(e);
    //        } finally {
    //            mSerialPort = null;
    //        }
    //
    //        if (isCallBackConnectFailed)
    //            onBPCConnectFailed();
    //    }
    //
    //    /** 写入数据 */
    //    public void writeData(byte[] value) {
    //        if (!isConnected())
    //            return;
    //
    //        Message message = mHandlerForWrite.obtainMessage(WHAT_WRITE);
    //        Bundle bundle = message.getData();
    //        bundle.putByteArray(KEY_DATA_WRITE, value);
    //        message.sendToTarget();
    //    }
    //
    //    /** 回调 串口连接成功 */
    //    protected abstract void onBPCConnectSuccess();
    //
    //    /** 回调 串口连接失败 */
    //    protected abstract void onBPCConnectFailed();
    //
    //    /** 回调 串口收到数据 */
    //    protected abstract void onBPCReceiveData(byte[] values);
    //
    //    @Override
    //    public boolean handleMessage(Message msg) {
    //        Bundle bundle = msg.peekData();
    //        if (bundle == null)
    //            return true;
    //
    //        switch (msg.what) {
    //            case WHAT_RECEIVE: {
    //                onBPCReceiveData(bundle.getByteArray(KEY_DATA_RECEIVE));
    //                break;
    //            }
    //            case WHAT_WRITE: {
    //                try {
    //                    mOutputStream.write(bundle.getByteArray(KEY_DATA_WRITE));
    //                } catch (Exception e) {
    //                    L.writeExceptionLog(e);
    //                }
    //                break;
    //            }
    //        }
    //        return true;
    //    }
    //
    //    /** 开始连接设备 */
    //    private void startConnectDevice(SPDeviceBean spDeviceBean) {
    //        SerialPortFinder serialPortFinder = new SerialPortFinder();
    //        String[] mDevices = serialPortFinder.getAllDevicesPath();
    //        if (mDevices.length == 0) {
    //            disconnectDevice(true);
    //            return;
    //        }
    //
    //        L.writeLog("扫描到的串口设备:" + Arrays.toString(mDevices));
    //
    //        boolean isMatch = false;
    //        for (String device : mDevices)
    //            if (TextUtils.equals(spDeviceBean.getDevicePath(), device)) {
    //                isMatch = true;
    //                break;
    //            }
    //
    //        if (!isMatch) {
    //            // 没有可用的串口设备
    //            disconnectDevice(true);
    //            return;
    //        }
    //
    //        try {
    //            mSerialPort = new SerialPort(new File(spDeviceBean.getDevicePath()), spDeviceBean.getDeviceBaudRate(), 0);
    //            mOutputStream = mSerialPort.getOutputStream();
    //            mSerialReadThread = new SerialReadThread(mSerialPort.getInputStream());
    //            mSerialReadThread.start();
    //            isConnected = true;
    //            onBPCConnectSuccess();
    //        } catch (Exception e) {
    //            L.writeExceptionLog(e);
    //            disconnectDevice(true);
    //        }
    //    }
    //
    //    /** 读串口线程 */
    //    private class SerialReadThread extends Thread {
    //        private BufferedInputStream mBis;
    //        private boolean isRun; // 是否运行
    //
    //        private SerialReadThread(InputStream is) {
    //            mBis = new BufferedInputStream(is);
    //            isRun = true;
    //        }
    //
    //        @Override
    //        public void run() {
    //            byte[] received = new byte[1024];
    //            int size;
    //
    //            while (isRun && !isInterrupted()) {
    //                try {
    //                    if (mBis.available() > 0) {
    //                        size = mBis.read(received);
    //                        if (size > 0) {
    //                            byte[] receivedSplit = new byte[size];
    //                            System.arraycopy(received, 0, receivedSplit, 0, size);
    //
    //                            Message message = mHandler.obtainMessage(WHAT_RECEIVE);
    //                            Bundle bundle = message.getData();
    //                            bundle.putByteArray(KEY_DATA_RECEIVE, receivedSplit);
    //                            message.sendToTarget();
    //                        }
    //                    } else {
    //                        SystemClock.sleep(1); // 暂停一点时间，避免一直循环造成CPU占用率过高
    //                    }
    //                } catch (Exception e) {
    //                    L.writeExceptionLog(e);
    //                }
    //            }
    //        }
    //
    //        /** 关闭读串口线程 */
    //        private void close() {
    //            isRun = false;
    //            interrupt();
    //            try {
    //                mBis.close();
    //            } catch (Exception e) {
    //                L.writeExceptionLog(e);
    //            }
    //        }
    //    }
    //
    //    /** 串口设备 Bean */
    //    private class SPDeviceBean {
    //        private String devicePath; // 串口设备
    //        private int deviceBaudRate; // 波特率
    //
    //        private SPDeviceBean(String devicePath, int deviceBaudRate) {
    //            this.devicePath = devicePath;
    //            this.deviceBaudRate = deviceBaudRate;
    //        }
    //
    //        private String getDevicePath() {
    //            return devicePath;
    //        }
    //
    //        private void setDevicePath(String devicePath) {
    //            this.devicePath = devicePath;
    //        }
    //
    //        private int getDeviceBaudRate() {
    //            return deviceBaudRate;
    //        }
    //
    //        private void setDeviceBaudRate(int deviceBaudRate) {
    //            this.deviceBaudRate = deviceBaudRate;
    //        }
    //    }

}
