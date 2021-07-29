package a.f.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a.f.base.BaseApp;
import a.f.base.BaseBean;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:14
 * 描述：SharedPreferences 轻量级存储 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SPUtils {



    private static volatile SPUtils mSPUtils; // 本类实例
    private Map<String, SPBean> mapSPBeans = new HashMap<>();

    private SPUtils() {
    }

    /** 获取 SPUtils 的实例 */
    public static SPUtils getInstance() {
        if (mSPUtils == null) {
            synchronized (SPUtils.class) {
                if (mSPUtils == null) {
                    mSPUtils = new SPUtils();
                }
            }
        }
        return mSPUtils;
    }

    /** 获取一个 SPBean */
    @SuppressLint("CommitPrefEdits")
    private SPBean getSPBean(String name) {
        if (mapSPBeans == null) {
            mapSPBeans = new HashMap<>();
        }

        SPBean spBean = mapSPBeans.get(name);
        if (spBean == null) {
            SharedPreferences sharedPreferences = BaseApp.getI().getSharedPreferences(name, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            spBean = new SPBean(sharedPreferences, editor);
            mapSPBeans.put(name, spBean);
        }
        return spBean;
    }

    /** 清空 某个 SharedPreferences 全部数据 */
    public void clearSPBeanData(String name) {
        try {
            getSPBean(name).getEditor().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 String */
    public String getString(String name, String key) {
        return getString(name, key, null);
    }

    /** 获取 String 需传默认值 */
    public String getString(String name, String key, String defValue) {
        try {
            return getSPBean(name).getSharedPreferences().getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /** 写入 String */
    public void setString(String name, String key, String value) {
        try {
            getSPBean(name).getEditor().putString(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 String */
    public void removeString(String name, String key) {
        try {
            getSPBean(name).getEditor().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 int */
    public int getInt(String name, String key) {
        return getInt(name, key, 0);
    }

    /** 获取 int 需传默认值 */
    public int getInt(String name, String key, int defValue) {
        try {
            return getSPBean(name).getSharedPreferences().getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /** 写入 int */
    public void setInt(String name, String key, int value) {
        try {
            getSPBean(name).getEditor().putInt(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 int */
    public void removeInt(String name, String key) {
        try {
            getSPBean(name).getEditor().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 boolean */
    public boolean getBoolean(String name, String key) {
        return getBoolean(name, key, false);
    }

    /** 获取 boolean 需传默认值 */
    public boolean getBoolean(String name, String key, boolean defValue) {
        try {
            return getSPBean(name).getSharedPreferences().getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /** 写入 boolean */
    public void setBoolean(String name, String key, boolean value) {
        try {
            getSPBean(name).getEditor().putBoolean(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 boolean */
    public void removeBoolean(String name, String key) {
        try {
            getSPBean(name).getEditor().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 float */
    public float getFloat(String name, String key) {
        return getFloat(name, key, 0);
    }

    /** 获取 float 需传默认值 */
    public float getFloat(String name, String key, float defValue) {
        try {
            return getSPBean(name).getSharedPreferences().getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /** 写入 float */
    public void setFloat(String name, String key, float value) {
        try {
            getSPBean(name).getEditor().putFloat(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 float */
    public void removeFloat(String name, String key) {
        try {
            getSPBean(name).getEditor().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 long */
    public long getLong(String name, String key) {
        return getLong(name, key, 0);
    }

    /** 获取 long 需传默认值 */
    public long getLong(String name, String key, long defValue) {
        try {
            return getSPBean(name).getSharedPreferences().getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /** 写入 long */
    public void setLong(String name, String key, long value) {
        try {
            getSPBean(name).getEditor().putLong(key, value).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 long */
    public void removeLong(String name, String key) {
        try {
            getSPBean(name).getEditor().remove(key).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 获取 指定Bean */
    public <T> T getBean(String name, String key, Class<T> classOfT) {
        String json = getString(name, key);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return GsonUtils.getInstance().fromJson(json, classOfT);
    }

    /** 写入 指定Bean */
    public void setBean(String name, String key, Object obj) {
        try {
            setString(name, key, GsonUtils.getInstance().getGson().toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 指定Bean */
    public void removeBean(String name, String key) {
        removeString(name, key);
    }

    /**
     * 获取 指定类型集合
     *
     * @param typeToken 指定类型 例子：new TypeToken<List<XxxBean>>(){}
     */
    public <T> List<T> getList(String name, String key, TypeToken<List<T>> typeToken) {
        String json = getString(name, key);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return GsonUtils.getInstance().fromJsonToList(json, typeToken);
    }

    /** 写入 指定类型集合 */
    public void setList(String name, String key, Object obj) {
        try {
            setString(name, key, GsonUtils.getInstance().getGson().toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 删除 指定类型集合 */
    public void removeList(String name, String key) {
        removeString(name, key);
    }

    /** SharedPreferences Bean */
    private class SPBean extends BaseBean {

        private SharedPreferences sharedPreferences; // 轻量级存储 实例
        private SharedPreferences.Editor editor; // 轻量级存储 编辑器

        public SPBean(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
            this.sharedPreferences = sharedPreferences;
            this.editor = editor;
        }

        public SharedPreferences getSharedPreferences() {
            return sharedPreferences;
        }

        public void setSharedPreferences(SharedPreferences sharedPreferences) {
            this.sharedPreferences = sharedPreferences;
        }

        public SharedPreferences.Editor getEditor() {
            return editor;
        }

        public void setEditor(SharedPreferences.Editor editor) {
            this.editor = editor;
        }

    }

}
