package a.f.utils;

import android.graphics.Typeface;
import android.util.SparseArray;
import android.widget.TextView;

import a.f.base.BaseApp;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:28
 * 描述：字体工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class AFTypefaceUtils {

    private static volatile AFTypefaceUtils mInstance; // 本类实例
    protected SparseArray<Typeface> fontFaces;

    protected AFTypefaceUtils() {
        fontFaces = new SparseArray<>();
    }

    /** 获取 AFTypefaceUtils 的实例 */
    public static AFTypefaceUtils getInstance() {
        if (mInstance == null) {
            synchronized (AFTypefaceUtils.class) {
                if (mInstance == null) {
                    mInstance = new AFTypefaceUtils();
                }
            }
        }
        return mInstance;
    }

    /** 获取 字体 */
    public Typeface getTypeface(int type) {
        Typeface typeface = fontFaces.get(type);
        if (typeface == null) {
            fontFaces.put(type, typeface = createTypeface(getPath(type)));
        }
        return typeface;
    }

    /** 给指定 TextView 设置一个字体 */
    public void setTypeface(TextView textView, int type) {
        textView.setTypeface(getTypeface(type));
    }

    /** 创建 Typeface */
    private Typeface createTypeface(String path) {
        return Typeface.createFromAsset(BaseApp.getI().getAssets(), path);
    }

    /** 根据类型获取字体路径 */
    protected String getPath(int type) {
        return null;
    }
}
