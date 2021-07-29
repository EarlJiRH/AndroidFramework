package a.f.utils.constant;

import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.spec.IvParameterSpec;

/**
 * ================================================
 * 类名：a.f.utils.constant
 * 时间：2021/7/20 17:29
 * 描述：静态常量类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public interface AFSF {


    // <editor-fold> ===== 配置 =====================================================================
    /** 设备所支持的架构 */
    String SUPPORTED_ABIS = Arrays.toString(Build.SUPPORTED_ABIS);

    /** IV of AES */
    /* of Upya */
    // IvParameterSpec IV = new IvParameterSpec("xxxxxxxxxxxxxxxx".getBytes(StandardCharsets.UTF_8));
    /* of ZHS */
    IvParameterSpec IV = new IvParameterSpec("zhs66666zhs88888".getBytes(StandardCharsets.UTF_8));

    /** 公钥 用于API接口 */
    /* of Upya */
    // String PUK = "xxxxxxxxxx";
    /* of ZHS */
    String PUK = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpggFbNc5G2yqOSEXmW7ZbHQpNZttCs/7oRivIHNFKEqcYICCI+q5+h9qpLsQQ0G/9+aU+ftIo1mCf4dy4TzpeBsMhhQFvoYBPUxBvubGb9OzcZhRYFtJTUBK+LQSApqE2oPtJ1VQHtJFJOJukq9Sx+8W9biYe2oP+9Ou7ey8GPwIDAQAB";

    /** 私钥 用于API接口 */
    /* of Upya */
    // String PRK = "xxxxxxxxxx";
    /* of ZHS */
    String PRK = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKmCAVs1zkbbKo5IReZbtlsdCk1m20Kz/uhGK8gc0UoSpxggIIj6rn6H2qkuxBDQb/35pT5+0ijWYJ/h3LhPOl4GwyGFAW+hgE9TEG+5sZv07NxmFFgW0lNQEr4tBICmoTag+0nVVAe0kUk4m6Sr1LH7xb1uJh7ag/7067t7LwY/AgMBAAECgYBWlCKhNdBQfK0101BworN3wlN6yWiAn3M5rG28Xou/q9tRBUfY70GlOzrCYzKd6/jn3lU6oIgzd5wOzcxxD3adr0K9pEGWunbcgm7rwCfrzrWyXNtSZB4m2tUrN5HnnmtEjUQYXbwHx8PiYpQwnSjjErlVur8h/qpgrOqoZBXbwQJBANJ2e/5z5RjiJfrn/dB8llM6WbqX5RnC+cG39XPOF6jRiYam/vxcAsMQtmnMxTdN2VDwENFbijI0n+S8DCf3Js8CQQDOLwcWcW+VKclHbdq1CW4kAMjLl/ybImKeZj1v/BBtpRIn5DHfEZTryMg7N8Z3ppz8yNpr9i6vKNjdxw3QkAWRAkEAvpjsy9B7PmC30pU8SA48OR1T22bp5L2tX+FQGVMgHBO+/0HHFBXjrnI5MHFwfRg0YVc3c9H5wmPkFcqzG5J5AwJAJ2n2AOROYEVMpMrrNBBJbbh4qfKz5zP7hiwlMnMM8OjNP7LvlaTWwvvuyuMunVDMUcZTeq/k3LcCPeL/UQ83AQJAG4zFU5AXGDWH4Zi2fzO5TMVybUWD8nkJFSiIeLku+4EXT9eCZj1HLSgqcWjrapmMW78eRshTg7cy0VyfG0YP5g==";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 检查更新App 后台服务 配置 =================================================
    /** 检查更新App 服务器地址 */
    String CUS_SERVIDE_URL = "http://zhskg.net/AppManage/";

    /** 检查更新App 接口 */
    String CUS_API_UPDATE = "AppManage";

    /** 检查更新App 意图 */
    String CUS_ACTION_UPDATE = "APP78678964";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 上传日志 后台服务 配置 ====================================================
    /** 上传日志 服务器地址 */
    String ULS_SERVIDE_URL = "http://zhskg.net/LogService/";

    /** 上传日志 接口 */
    String ULS_API_UPLOAD_LOG = "upya";

    /** 上传日志 意图 */
    String ULS_ACTION_UPLOAD_LOG = "7867DA4A";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 接口地址 =================================================================
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 普通常量 =================================================================
    /** 随机对象 */
    Random RANDOM = new Random();
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 常量数组 =================================================================
    /** 手机号头部 */
    String[] PHONE_HEADER = {"130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "145", "147", "149", "150", "151", "152", "153", "155", "156", "157", "158", "159", "170", "171", "173", "175", "176", "177", "178", "180", "181", "182", "183", "184", "185", "186", "187", "188", "189"};

    /** 图片格式 */
    String[] IMAGE_FORMAT = {".jpg", ".jpeg", ".png", ".bmp", ".gif", ".pcx", ".tiff", ".tga", ".exif", ".fpx", ".svg", ".psd", ".cdr", ".pcd", ".dxf", ".ufo", ".eps", ".ai", ".hdri", ".raw"};

    /** 十六进制字符 */
    String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /** 常见姓氏 数组 */
    String[] FAMILY_NAMES = {"李", "王", "张", "刘", "陈", "杨", "黄", "赵", "周", "吴", "徐", "孙", "朱", "马", "胡", "郭", "林", "何", "高", "梁", "郑", "罗", "宋", "谢", "唐", "韩", "曹", "许", "邓", "萧", "冯", "曾", "程", "蔡", "彭", "潘", "袁", "于", "董", "余", "苏", "叶", "吕", "魏", "蒋", "田", "杜", "丁", "沈", "姜", "范", "江", "傅", "钟", "卢", "汪", "戴", "崔", "任", "陆", "廖", "姚", "方", "金", "邱", "夏", "谭", "韦", "贾", "邹", "石", "熊", "孟", "秦", "阎", "薛", "侯", "雷", "白", "龙", "段", "郝", "孔", "邵", "史", "毛", "常", "万", "顾", "赖", "武", "康", "贺", "严", "尹", "钱", "施", "牛", "洪", "龚", "汤", "陶", "黎", "温", "莫", "易", "樊", "乔", "文", "安", "殷", "颜", "庄", "章", "鲁", "倪", "庞", "邢", "俞", "翟", "蓝", "聂", "齐", "向", "申", "葛", "岳"};
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== KEY =====================================================================
    /** KEY 是否是前台服务 */
    String KEY_IS_FOREGROUND_SERVICE = "is_foreground_service";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== SP KEY ==================================================================
    /** 文件名 检查更新 */
    String SP_NAME_CHECK_UPDATE = "check_update";

    /** KEY 检查更新日期 */
    String SP_KEY_CHECK_UPDATE_DATE = "check_update_date";

    /** KEY 检查更新版本 */
    String SP_KEY_CHECK_UPDATE_VERSION = "check_update_version";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 符号 ====================================================================
    /** 符号 文件分隔符 【forward slash = /】 */
    String S_FS = File.separator;

    /** 符号 换行 */
    String S_LN = "\n";

    /** 符号 换行 */
    String S_LNx2 = "\n\n";

    /** 符号 换行 */
    String S_LNx3 = "\n\n\n";

    /** 符号 换行 */
    String S_LNx4 = "\n\n\n\n";

    /** 符号 换行 */
    String S_LNx5 = "\n\n\n\n\n";

    /** 符号 换行 */
    String S_LNx10 = "\n\n\n\n\n\n\n\n\n\n";

    /** 符号 间隔号 */
    String S_INTERPUNCT = "·";

    /** 符号 波浪号/腭化符号 */
    String S_TILDE = "~";

    /** 符号 感叹号(英文) */
    String S_EXCLAMATION_EN = "!";

    /** 符号 感叹号(中文) */
    String S_EXCLAMATION_ZHCN = "！";

    /** 符号 question mark 问号(英文) */
    String S_QM_EN = "?";

    /** 符号 question mark 问号(中文) */
    String S_QM_ZHCN = "？";

    /** 符号 at */
    String S_AT = "@";

    /** 符号 井号 */
    String S_WELL = "#";

    /** 符号 百分号 */
    String S_PERCENT = "%";

    /** 符号 帽子/控制符/上箭头 */
    String S_HAT = "^";

    /** 符号 省略号 */
    String S_OMIT = "…";

    /** 符号 和/与/并且 */
    String S_AND = "&";

    /** 符号 乘号/星号 */
    String S_MULT = "*";

    /** 符号 连字符 en dash */
    String S_EN_DASH = "—";

    /** 符号 减号/连字符 短 en dash short */
    String S_SUBTRACTION = "-";

    /** 符号 下划线 */
    String S_UNDERLINE = "_";

    /** 符号 加号 */
    String S_ADDITION = "+";

    /** 符号 等号 */
    String S_EQUALITY = "=";

    /** 符号 竖杠/位或 */
    String S_VERTICAL = "|";

    /** 符号 冒号(英文) */
    String S_COLON_EN = ":";

    /** 符号 冒号(中文) */
    String S_COLON_ZHCN = "：";

    /** 符号 分号(英文) */
    String S_SEMICOLON_EN = ";";

    /** 符号 分号(中文) */
    String S_SEMICOLON_ZHCN = "；";

    /** 符号 小于号 less-than */
    String S_LT = "<";

    /** 符号 大于号 greater-than */
    String S_GT = ">";

    /** 符号 逗号(英文) */
    String S_COMMA_EN = ",";

    /** 符号 逗号(中文) */
    String S_COMMA_ZHCN = "，";

    /** 符号 书名号 left */
    String S_GUILLEMET_L = "《";

    /** 符号 书名号 right */
    String S_GUILLEMET_R = "》";

    /** 符号 small brackets 小括号 左 */
    String S_SB_L = "(";

    /** 符号 small brackets 小括号 右 */
    String S_SB_R = ")";

    /** 符号 medium brackets 中括号(英文) 左 */
    String S_MB_EN_L = "[";

    /** 符号 medium brackets 中括号(英文) 右 */
    String S_MB_EN_R = "]";

    /** 符号 medium brackets 中括号(中文) 左 */
    String S_MB_ZHCN_L = "【";

    /** 符号 medium brackets 中括号(中文) 右 */
    String S_MB_ZHCN_R = "】";

    /** 符号 big brackets 大括号 左 */
    String S_BB_L = "{";

    /** 符号 big brackets 大括号 右 */
    String S_BB_R = "}";

    /** 符号 句号 */
    String S_FULL_STOP = "。";

    /** 符号 点 */
    String S_DOT = ".";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 文件 =====================================================================
    /** 文件 App数据库 application_id.db */
    String FILE_APPLICATION_ID$DB = AFConfig.APP_PACKAGE_NAME + ".db";

    /** 文件 App数据库 application_id.db （框架所用数据） */
    String FILE_APPLICATION_ID$DB_AF =  "a.f.db";

    /** 文件 日志 XXX.log */
    String FILE_DT$LOG = ".log";

    /** 文件 安装包 XXX.apk */
    String FILE_$APK = ".apk";

    /** 文件 安装包 app.apk */
    String FILE_APP$APK = "app.apk";

    /** 文件 异常日志 exception */
    String FILE_EXCEPTION_LOG = "exception";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 文件夹 ===================================================================
    /** Android 项目资源 */
    String ANDROID_RESOURCE = "android.resource://" + AFConfig.APP_PACKAGE_NAME + S_FS;

    /** 文件夹 SD 卡根目录 */
    String FOLDER_SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    /** 文件夹 APP 外部存储 */
    String FOLDER_APP = "." + AFConfig.APP_PACKAGE_NAME;

    /** 文件夹 日志存储 */
    String FOLDER_LOG = "log";

    /** 文件夹 图片存储 */
    String FOLDER_IMAGE_CACHE = "image";

    /** 文件夹 照片缓存 */
    String FOLDER_PHOTO_CACHE = "photo";

    /** 文件夹 下载存储 */
    String FOLDER_DOWNLOAD = "download";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 文件路径 =================================================================
    /** 文件路径 APK 文件储存路径 */
    String PATH_FILE_APP$APK = FOLDER_SD_PATH + S_FS + FOLDER_APP + S_FS + FOLDER_DOWNLOAD + S_FS + FILE_APP$APK;
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 文件夹路径 ===============================================================
    /** 文件夹路径 日志目录 */
    String PATH_FOLDER_LOG = FOLDER_SD_PATH + S_FS + FOLDER_APP + S_FS + FOLDER_LOG;

    /** 文件夹路径 照片目录 */
    String PATH_FOLDER_PHOTO = FOLDER_SD_PATH + S_FS + FOLDER_APP + S_FS + FOLDER_IMAGE_CACHE + S_FS + FOLDER_PHOTO_CACHE;
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 时间 毫秒 ================================================================
    /** 1秒 */
    long TIME_1S = 1000;

    /** 2秒 */
    long TIME_2S = TIME_1S * 2;

    /** 3秒 */
    long TIME_3S = TIME_1S * 3;

    /** 4秒 */
    long TIME_4S = TIME_1S * 4;

    /** 5秒 */
    long TIME_5S = TIME_1S * 5;

    /** 10秒 */
    long TIME_10S = TIME_1S * 10;

    /** 20秒 */
    long TIME_20S = TIME_10S * 2;

    /** 30秒 */
    long TIME_30S = TIME_10S * 3;

    /** 40秒 */
    long TIME_40S = TIME_10S * 4;

    /** 50秒 */
    long TIME_50S = TIME_10S * 5;

    /** 1分钟 */
    long TIME_1M = TIME_10S * 6;

    /** 2分钟 */
    long TIME_2M = TIME_1M * 2;

    /** 3分钟 */
    long TIME_3M = TIME_1M * 3;

    /** 4分钟 */
    long TIME_4M = TIME_1M * 4;

    /** 5分钟 */
    long TIME_5M = TIME_1M * 5;

    /** 10分钟 */
    long TIME_10M = TIME_1M * 10;

    /** 20分钟 */
    long TIME_20M = TIME_10M * 2;

    /** 30分钟 */
    long TIME_30M = TIME_10M * 3;

    /** 40分钟 */
    long TIME_40M = TIME_10M * 4;

    /** 50分钟 */
    long TIME_50M = TIME_10M * 5;

    /** 1小时 */
    long TIME_1H = TIME_10M * 6;

    /** 2小时 */
    long TIME_2H = TIME_1H * 2;

    /** 3小时 */
    long TIME_3H = TIME_1H * 3;

    /** 4小时 */
    long TIME_4H = TIME_1H * 4;

    /** 5小时 */
    long TIME_5H = TIME_1H * 5;

    /** 6小时 */
    long TIME_6H = TIME_1H * 6;

    /** 7小时 */
    long TIME_7H = TIME_1H * 7;

    /** 8小时 */
    long TIME_8H = TIME_1H * 8;

    /** 9小时 */
    long TIME_9H = TIME_1H * 9;

    /** 10小时 */
    long TIME_10H = TIME_1H * 10;

    /** 11小时 */
    long TIME_11H = TIME_1H * 11;

    /** 12小时 */
    long TIME_12H = TIME_1H * 12;

    /** 13小时 */
    long TIME_13H = TIME_1H * 13;

    /** 14小时 */
    long TIME_14H = TIME_1H * 14;

    /** 15小时 */
    long TIME_15H = TIME_1H * 15;

    /** 16小时 */
    long TIME_16H = TIME_1H * 16;

    /** 17小时 */
    long TIME_17H = TIME_1H * 17;

    /** 18小时 */
    long TIME_18H = TIME_1H * 18;

    /** 19小时 */
    long TIME_19H = TIME_1H * 19;

    /** 20小时 */
    long TIME_20H = TIME_1H * 20;

    /** 21小时 */
    long TIME_21H = TIME_1H * 21;

    /** 22小时 */
    long TIME_22H = TIME_1H * 22;

    /** 23小时 */
    long TIME_23H = TIME_1H * 23;

    /** 1天 */
    long TIME_1D = TIME_1H * 24;

    /** 2天 */
    long TIME_2D = TIME_1D * 2;

    /** 3天 */
    long TIME_3D = TIME_1D * 3;

    /** 4天 */
    long TIME_4D = TIME_1D * 4;

    /** 5天 */
    long TIME_5D = TIME_1D * 5;

    /** 6天 */
    long TIME_6D = TIME_1D * 6;

    /** 7天 */
    long TIME_7D = TIME_1D * 7;

    /** 8天 */
    long TIME_8D = TIME_1D * 8;

    /** 9天 */
    long TIME_9D = TIME_1D * 9;

    /** 10天 */
    long TIME_10D = TIME_1D * 10;

    /** 20天 */
    long TIME_20D = TIME_10D * 2;

    /** 30天 */
    long TIME_30D = TIME_10D * 3;

    /** 40天 */
    long TIME_40D = TIME_10D * 4;

    /** 50天 */
    long TIME_50D = TIME_10D * 5;

    /** 60天 */
    long TIME_60D = TIME_10D * 6;

    /** 70天 */
    long TIME_70D = TIME_10D * 7;

    /** 80天 */
    long TIME_80D = TIME_10D * 8;

    /** 90天 */
    long TIME_90D = TIME_10D * 9;

    /** 100天 */
    long TIME_100D = TIME_10D * 10;
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 日期时间 格式 DateTime ====================================================
    /** DateTime 2016-07-08 09:10:11 */
    String DT_001 = "yyyy-MM-dd HH:mm:ss";

    /** DateTime 2016-07-08 09:10 */
    String DT_002 = "yyyy-MM-dd HH:mm";

    /** DateTime 2016-07-08 */
    String DT_003 = "yyyy-MM-dd";

    /** DateTime 09:10:11 */
    String DT_004 = "HH:mm:ss";

    /** DateTime 2016-07 */
    String DT_005 = "yyyy-MM";

    /** DateTime 07-08 */
    String DT_006 = "MM-dd";

    /** DateTime 09:10 */
    String DT_007 = "HH:mm";

    /** DateTime 10:11 */
    String DT_008 = "mm:ss";

    /** DateTime 2016 */
    String DT_009 = "yyyy";

    /** DateTime 07 */
    String DT_010 = "MM";

    /** DateTime 08 */
    String DT_011 = "dd";

    /** DateTime 09 */
    String DT_012 = "HH";

    /** DateTime 10 */
    String DT_013 = "mm";

    /** DateTime 11 */
    String DT_014 = "ss";

    /** DateTime 2016年07月08日 09时10分11秒 */
    String DT_015 = "yyyy年MM月dd日 HH时mm分ss秒";

    /** DateTime 2016年07月08日 09时10分 */
    String DT_016 = "yyyy年MM月dd日 HH时mm分";

    /** DateTime 2016年07月08日 */
    String DT_017 = "yyyy年MM月dd日";

    /** DateTime 09时10分11秒 */
    String DT_018 = "HH时mm分ss秒";

    /** DateTime 2016年07月 */
    String DT_019 = "yyyy年MM月";

    /** DateTime 07月08日 */
    String DT_020 = "MM月dd日";

    /** DateTime 09时10分 */
    String DT_021 = "HH时mm分";

    /** DateTime 10分11秒 */
    String DT_022 = "mm分ss秒";

    /** DateTime 2016年 */
    String DT_023 = "yyyy年";

    /** DateTime 07月 */
    String DT_024 = "MM月";

    /** DateTime 08日 */
    String DT_025 = "dd日";

    /** DateTime 09时 */
    String DT_026 = "HH时";

    /** DateTime 10分 */
    String DT_027 = "mm分";

    /** DateTime 11秒 */
    String DT_028 = "ss秒";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 数字格式 NumberFormat ====================================================
    /** NumberFormat 123456789 */
    String NF_001 = "#0";

    /** NumberFormat 12345678.9 */
    String NF_002 = "#0.0";

    /** NumberFormat 1234567.89 */
    String NF_003 = "#0.00";

    /** NumberFormat 123456.789 */
    String NF_004 = "#0.000";

    /** NumberFormat 12345.6789 */
    String NF_005 = "#0.0000";

    /** NumberFormat 123,456,789 */
    String NF_006 = "#,##0";

    /** NumberFormat 12,345,678.9 */
    String NF_007 = "#,##0.0";

    /** NumberFormat 1,234,567.89 */
    String NF_008 = "#,##0.00";

    /** NumberFormat 123,456.789 */
    String NF_009 = "#,##0.000";

    /** NumberFormat 12,345.6789 */
    String NF_010 = "#,##0.0000";

    /** NumberFormat 00000001 */
    String NF_011 = "00000000";
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 数据长度 文件大小 =========================================================
    /** 1 Byte */
    long DL_1B = 1;

    /** 1 KB/Kilobyte */
    long DL_1KB = DL_1B * 1024;

    /** 1 MB/Megabytes */
    long DL_1MB = DL_1KB * 1024;

    /** 10 MB/Megabytes */
    long DL_10MB = DL_1MB * 10;

    /** 1 GB/Gigabyte */
    long DL_1GB = DL_1MB * 1024;

    /** 1 TB/Terabyte */
    long DL_1TB = DL_1GB * 1024;
    // </editor-fold> ==============================================================================


    // <editor-fold> ===== 正则规则 =================================================================
    /** 正则 手机号 */
    String REGEX_PHONE = "^[1][0-9]{10}$";

    /** 正则 银行卡 */
    String REGEX_BANK_CARD = "^[0-9]{16,19}$";

    /** 正则 姓名2-10位 */
    String REGEX_CHINA_NAME = "^([\u4e00-\u9fa5]{2,10})$";

    /** 正则 身份证号码 */
    String REGEX_ID_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    /** 正则 邮箱 */
    String REGEX_MAILBOX = "^[A-Z0-9a-z._%+-]+@[A-Z0-9a-z.-]+\\.[A-Za-z]{2,4}$";

    /** 正则 数字 */
    String REGEX_NUMBER = "^[0-9]*$";

    /** 正则 字母 */
    String REGEX_ASC = "^[a-zA-Z]*$";
    // </editor-fold> ==============================================================================

}
