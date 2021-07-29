package a.f.utils.secret;

import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

/**
 * ================================================
 * 类名：a.f.utils.secret
 * 时间：2021/7/20 17:36
 * 描述：RSA 加解密工具
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class RSAUtils {
    private static volatile RSAUtils mRSAUtils; // 本类实例
    private final int MAX_ENCRYPT_BLOCK = 117; // RSA最大加密明文大小
    private final int MAX_DECRYPT_BLOCK = 128; // RSA最大解密密文大小
    private final String KEY_ALGORITHM = "RSA"; // 加密算法RSA
    private final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"; // 加密/解密算法/工作模式/填充方式
    private Cipher mCipher;

    private RSAUtils() {
        try {
            mCipher = Cipher.getInstance(CIPHER_ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 RSAUtils 的实例 */
    public static RSAUtils getInstance() {
        if (mRSAUtils == null) {
            synchronized (RSAUtils.class) {
                if (mRSAUtils == null) {
                    mRSAUtils = new RSAUtils();
                }
            }
        }
        return mRSAUtils;
    }

    /** 公钥加密 public key */
    public synchronized String encryptPUK(String puk, String data) {
        try {
            if (TextUtils.isEmpty(puk) || TextUtils.isEmpty(data)) {
                throw new NullPointerException("puk or data is null");
            }

            mCipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(puk, Base64.DEFAULT))));

            // 对数据分段加密
            byte[] dataBytes = data.getBytes();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int offSet = 0; dataBytes.length - offSet > 0; offSet += MAX_ENCRYPT_BLOCK) {
                byte[] buffer = dataBytes.length - offSet > MAX_ENCRYPT_BLOCK ? mCipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK) : mCipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                baos.write(buffer, 0, buffer.length);
            }
            byte[] encryptData = baos.toByteArray();
            baos.flush();
            baos.close();
            return new String(Base64.encode(encryptData, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 私钥签名 private key */
    public synchronized String encryptPRK(String prk, String data) {
        try {
            if (TextUtils.isEmpty(prk) || TextUtils.isEmpty(data)) {
                throw new NullPointerException("prk or data is null");
            }

            mCipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(prk, Base64.DEFAULT))));

            // 对数据分段加密
            byte[] dataBytes = data.getBytes();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int offSet = 0; dataBytes.length - offSet > 0; offSet += MAX_ENCRYPT_BLOCK) {
                byte[] buffer = dataBytes.length - offSet > MAX_ENCRYPT_BLOCK ? mCipher.doFinal(dataBytes, offSet, MAX_ENCRYPT_BLOCK) : mCipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                baos.write(buffer, 0, buffer.length);
            }
            byte[] encryptData = baos.toByteArray();
            baos.flush();
            baos.close();
            return new String(Base64.encode(encryptData, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 公钥验证签名 public key */
    public synchronized String decryptPUK(String puk, String data) {
        try {
            if (TextUtils.isEmpty(puk) || TextUtils.isEmpty(data)) {
                throw new NullPointerException("puk or data is null");
            }

            mCipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(Base64.decode(puk, Base64.DEFAULT))));

            // 对数据分段解密
            byte[] dataBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int offSet = 0; dataBytes.length - offSet > 0; offSet += MAX_DECRYPT_BLOCK) {
                byte[] buffer = dataBytes.length - offSet > MAX_DECRYPT_BLOCK ? mCipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK) : mCipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                baos.write(buffer, 0, buffer.length);
            }
            byte[] decryptData = baos.toByteArray();
            baos.flush();
            baos.close();
            return new String(decryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 私钥解密 private key */
    public synchronized String decryptPRK(String prk, String data) {
        try {
            if (TextUtils.isEmpty(prk) || TextUtils.isEmpty(data)) {
                throw new NullPointerException("prk or data is null");
            }

            mCipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(prk, Base64.DEFAULT))));

            // 对数据分段解密
            byte[] dataBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int offSet = 0; dataBytes.length - offSet > 0; offSet += MAX_DECRYPT_BLOCK) {
                byte[] buffer = dataBytes.length - offSet > MAX_DECRYPT_BLOCK ? mCipher.doFinal(dataBytes, offSet, MAX_DECRYPT_BLOCK) : mCipher.doFinal(dataBytes, offSet, dataBytes.length - offSet);
                baos.write(buffer, 0, buffer.length);
            }
            byte[] decryptData = baos.toByteArray();
            baos.flush();
            baos.close();
            return new String(decryptData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /** 获取公钥和私钥 */
    public synchronized List<String> getKeys() {
        List<String> list = new ArrayList<>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            list.add(new String(Base64.encode(keyPair.getPublic().getEncoded(), Base64.DEFAULT)));
            list.add(new String(Base64.encode(keyPair.getPrivate().getEncoded(), Base64.DEFAULT)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
