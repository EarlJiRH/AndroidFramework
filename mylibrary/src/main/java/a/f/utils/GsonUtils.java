package a.f.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.List;

import a.f.bean.response.BaseJson;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:25
 * 描述：GsonUtils 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class GsonUtils {

    private static volatile GsonUtils mGsonUtils; // 本类实例
    private Gson mGson; // Gson单例

    private GsonUtils() {
        mGson = new Gson();
    }

    /** 获取 GsonUtils 的实例 */
    public static GsonUtils getInstance() {
        if (mGsonUtils == null) {
            synchronized (GsonUtils.class) {
                if (mGsonUtils == null) {
                    mGsonUtils = new GsonUtils();
                }
            }
        }
        return mGsonUtils;
    }

    /** 获取 Gson 单例 */
    public Gson getGson() {
        return mGson;
    }

    /** 已拦截异常 对象转Json字符串 */
    public String toJson(Object src) {
        try {
            return getGson().toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 已拦截异常 该方法将指定的Json反序列化为指定类的对象 */
    public <T> T fromJson(String json, Class<T> classOfT) {
        T obj = null;
        try {
            obj = mGson.fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 已拦截异常 该方法将指定的Json反序列化为指定类的对象的集合
     *
     * @param typeToken 指定类型 例子：new TypeToken<List<XxxBean>>(){}
     */
    public <T> List<T> fromJsonToList(String json, TypeToken<List<T>> typeToken) {
        List<T> obj = null;
        try {
            obj = mGson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 已拦截异常 该方法将指定的Json反序列化为BaseJson类的泛型对象
     *
     * @param typeToken 指定类型（例子：new TypeToken<BaseJson<XxxBean>>(){}）
     */
    public <T> BaseJson<T> fromJsonToBaseJson(String json, TypeToken<BaseJson<T>> typeToken) {
        BaseJson<T> obj = null;
        try {
            obj = mGson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    /**
     * 已拦截异常 该方法将指定的Json反序列化为BaseJson<List>类的数组泛型对象
     *
     * @param typeToken 指定类型（例子：new TypeToken<BaseJson<List<XxxBean>>>(){}）
     */
    public <T> BaseJson<List<T>> fromJsonToBaseJsonList(String json, TypeToken<BaseJson<List<T>>> typeToken) {
        BaseJson<List<T>> obj = null;
        try {
            obj = mGson.fromJson(json, typeToken.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    // =============================================================================================

    /** Json字符串转org.json.JSONObject */
    public static JSONObject jsonToJSONObject(String json) {
        JSONObject params = null;
        try {
            params = new JSONObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

}
