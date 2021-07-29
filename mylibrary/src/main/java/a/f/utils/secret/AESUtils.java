package a.f.utils.secret;

import android.text.TextUtils;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * ================================================
 * 类名：a.f.utils.secret
 * 时间：2021/7/20 17:35
 * 描述：AES 加解密工具
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class AESUtils {

    private static volatile AESUtils mAESUtils; // 本类实例
    private final String KEY_ALGORITHM = "AES"; // 密钥算法
    private final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding"; // 加解密算法/工作模式/填充方式 JAVA6 支持PKCS5PADDING填充方式 Bouncy castle支持PKCS7Padding填充方式
    private Cipher mCipher;

    private AESUtils() {
        try {
            mCipher = Cipher.getInstance(CIPHER_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 AESUtils 的实例 */
    public static AESUtils getInstance() {
        if (mAESUtils == null) {
            synchronized (AESUtils.class) {
                if (mAESUtils == null) {
                    mAESUtils = new AESUtils();
                }
            }
        }
        return mAESUtils;
    }

    /** 加密 【key = 16位随机字符串】【data = 明文】 */
    public synchronized String encrypt(String key, String data, IvParameterSpec iv) {
        return execute(Cipher.ENCRYPT_MODE, key, data, iv);
    }

    /** 解密 【key = 16位随机字符串】【data = 密文】 */
    public synchronized String decrypt(String key, String data, IvParameterSpec iv) {
        return execute(Cipher.DECRYPT_MODE, key, data, iv);
    }

    /** 执行任务 */
    private synchronized String execute(int mode, String key, String data, IvParameterSpec iv) {
        try {
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(data)) {
                throw new NullPointerException("key or data is null");
            }

            mCipher.init(mode, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM), iv);
            if (mode == Cipher.ENCRYPT_MODE) {
                return Base64.encodeToString(mCipher.doFinal(data.getBytes(StandardCharsets.UTF_8)), Base64.DEFAULT);
            } else {
                return new String(mCipher.doFinal(Base64.decode(data, Base64.DEFAULT)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
