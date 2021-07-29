package a.f.utils.constant;

import org.apache.commons.io.IOUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * ================================================
 * 类名：a.f.utils.constant
 * 时间：2021/7/20 17:20
 * 描述：配置文件
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class AFConfig {

    public static String APP_NAME;
    public static String APP_PACKAGE_NAME;
    public static String UPYA_KEY_ID;
    public static String CHANNEL;
    public static String LOG_TAG;
    public static boolean LOG_PRINT;
    public static boolean LOG_WRITE_UPLOAD;
    public static boolean PERMIT_PROXY;

    static {
        InputStreamReader isr = null;
        try {
            Properties properties = new Properties();
            properties.load(isr = new InputStreamReader(AFConfig.class.getResourceAsStream("/assets/AFConfig"), StandardCharsets.UTF_8));
            APP_NAME = properties.getProperty("appName");
            APP_PACKAGE_NAME = properties.getProperty("appPackageName");
            UPYA_KEY_ID = properties.getProperty("upyaKeyID");
            CHANNEL = properties.getProperty("channel");
            LOG_TAG = properties.getProperty("logTag");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            IOUtils.closeQuietly(isr);
        }
    }

    /**
     * 初始化值（该方法必须在所有其它工具类初始化之前调用）
     *
     * @param logPrint       是否打印普通日志
     * @param logWriteUpload 是否写&上传日志
     * @param permitProxy    是否允许使用代理
     */
    public static void initValue(boolean logPrint, boolean logWriteUpload, boolean permitProxy) {
        LOG_PRINT = logPrint;
        LOG_WRITE_UPLOAD = logWriteUpload;
        PERMIT_PROXY = permitProxy;
    }
}
