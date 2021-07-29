package a.f.utils.secret;

import java.util.Random;

import javax.crypto.spec.IvParameterSpec;

import a.f.bean.common.secret.SecretBean;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils.secret
 * 时间：2021/7/20 17:35
 * 描述：加解密 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class EncryptionUtils {

    private static volatile EncryptionUtils mEncryptionUtils; // 本类实例
    private final String RANDOM_RAW = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int RANDOM_SIZE = RANDOM_RAW.length();
    private Random mRandom = AFSF.RANDOM;

    private EncryptionUtils() {
    }

    /** 获取 EncryptionUtils 的实例 */
    public static EncryptionUtils getInstance() {
        if (mEncryptionUtils == null) {
            synchronized (EncryptionUtils.class) {
                if (mEncryptionUtils == null) {
                    mEncryptionUtils = new EncryptionUtils();
                }
            }
        }
        return mEncryptionUtils;
    }

    /** 普通字符串加密 【plainText = 明文】 */
    public synchronized String encodeData(String plainText, String key, IvParameterSpec iv) {
        return AESUtils.getInstance().encrypt(key, plainText, iv);
    }

    /** 普通字符串解密 【cipherText = 密文】 */
    public synchronized String decodeData(String cipherText, String key, IvParameterSpec iv) {
        return AESUtils.getInstance().decrypt(key, cipherText, iv);
    }

    /** 双重加密数据 */
    public synchronized SecretBean encodeDataDouble(SecretBean secretBean, String data, String puk, IvParameterSpec iv) {
        String sign = getRandomString(); // 原始16位签名
        secretBean.setSign(RSAUtils.getInstance().encryptPUK(puk, sign)); // 加密后的16位签名
        secretBean.setData(AESUtils.getInstance().encrypt(sign, data, iv)); // 加密后的数据
        return secretBean;
    }

    /** 双重解密数据 */
    public synchronized String decodeDataDouble(SecretBean secretBean, String prk, IvParameterSpec iv) {
        String signDecode = RSAUtils.getInstance().decryptPRK(prk, secretBean.getSign()); // 解密后的16位签名
        return AESUtils.getInstance().decrypt(signDecode, secretBean.getData(), iv); // 解密后的数据
    }

    /** 将号码简单加密；可用于将身份证号码简单加密 */
    public synchronized String numEncrypt(String num) {
        try {
            StringBuilder sb = new StringBuilder(num);
            switch (sb.length()) {
                case 0:
                case 1:
                    break;
                case 2:
                case 3:
                    num = sb.replace(1, 2, "*").toString();
                    break;
                case 4:
                    num = sb.replace(1, 3, "**").toString();
                    break;
                case 5:
                    num = sb.replace(1, 4, "***").toString();
                    break;
                case 6:
                case 7:
                    num = sb.replace(1, 5, "****").toString();
                    break;
                case 8:
                    num = sb.replace(2, 6, "****").toString();
                    break;
                case 9:
                    num = sb.replace(2, 7, "*****").toString();
                    break;
                case 18:
                    num = sb.replace(3, 17, "*** **** **** ***").toString();
                    break;
                default:
                    num = sb.replace(3, sb.length() - 3, "****").toString();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /** 获取16位长度的随机字符串 */
    public synchronized String getRandomString() {
        return getRandomString(16);
    }

    /**
     * 获取随机字符串
     *
     * @param length 字符串长度
     */
    public synchronized String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_RAW.charAt(mRandom.nextInt(RANDOM_SIZE)));
        }
        return sb.toString();
    }
}
