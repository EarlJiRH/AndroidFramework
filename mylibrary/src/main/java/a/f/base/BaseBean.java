package a.f.base;


import java.io.Serializable;

import a.f.utils.GsonUtils;

/**
 * ================================================
 * 类名：a.f.base
 * 时间：2021/7/20 16:30
 * 描述：BaseBean 基类 所有其它 Bean 都应 继承此类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class BaseBean implements Serializable {

    /** 将本类转为 Json 字符串 */
    public String toJson() {
        return GsonUtils.getInstance().getGson().toJson(this);
    }
}
