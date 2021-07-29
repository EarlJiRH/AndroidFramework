package a.f.ui.imagebrowse;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

/**
 * ================================================
 * 类名：a.f.ui.imagebrowse
 * 时间：2021/7/20 16:57
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public interface ImageBrowseContract {


    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

    }
}
