package a.f.utils;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import a.f.base.BaseApp;
import a.f.bean.common.judgement.DataBean;
import a.f.bean.common.judgement.SelectBean;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils
 * 时间：2021/7/20 17:27
 * 描述：变量 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class AFVariableUtils {

    /** 快捷获取 All Type List */
    @SafeVarargs
    public static <T> List<T> getListForAllType(T... t) {
        List<T> values = new ArrayList<>();
        if (t != null) {
            Collections.addAll(values, t);
        }
        return values;
    }

    /** 快捷获取 All Type ArrayList */
    @SafeVarargs
    public static <T> ArrayList<T> getArrayListForAllType(T... t) {
        ArrayList<T> values = new ArrayList<>();
        if (t != null) {
            Collections.addAll(values, t);
        }
        return values;
    }

    /** 获取假数据列表 默认10个 */
    public static List<DataBean> getFakeDataList() {
        return getFakeDataList(10);
    }

    /**
     * 获取假数据列表
     *
     * @param count 假数据数量
     */
    public static List<DataBean> getFakeDataList(int count) {
        List<DataBean> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new DataBean());
        }
        return list;
    }

    /** 快捷获取 拍照来源 */
    public static List<SelectBean> getPhotoForm(boolean isHaveSeenOriginal) {
        List<SelectBean> list = new ArrayList<>();
        list.add(new SelectBean(1, "拍照"));
        list.add(new SelectBean(2, "从相册中选择"));
        if (isHaveSeenOriginal) {
            list.add(new SelectBean(3, "查看原图"));
        }
        return list;
    }

    /** 快捷获取 图片操作选项 */
    public static List<SelectBean> getImageOperation() {
        return getListForAllType(new SelectBean(1, "保存图片"));
    }

    /** 快捷获取 性别选项 */
    public static List<SelectBean> getSexOption() {
        return getListForAllType(
                new SelectBean(1, "男"),
                new SelectBean(2, "女")
        );
    }

    /** 快捷获取 浏览器更多选项 */
    public static List<SelectBean> getWebPageMore() {
        return getListForAllType(
                new SelectBean(1, "复制链接"),
                new SelectBean(2, "在浏览器中打开"),
                new SelectBean(3, "刷新")
        );
    }

    /** 获取一个随机手机号码 */
    public static String getRandomPhone() {
        return AFSF.PHONE_HEADER[AFSF.RANDOM.nextInt(AFSF.PHONE_HEADER.length)] + NumberUtils.numberFormat(AFSF.NF_011, String.valueOf(AFSF.RANDOM.nextInt(100000000)));
    }

    /** 获取一个随机姓名 */
    public static String getRandomFullName() {
        StringBuilder sb = new StringBuilder(AFSF.FAMILY_NAMES[AFSF.RANDOM.nextInt(AFSF.FAMILY_NAMES.length)]);
        if (AFSF.RANDOM.nextBoolean()) {
            sb.append((char) (AFSF.RANDOM.nextInt(20902) + 19968));
        } else {
            sb.append((char) (AFSF.RANDOM.nextInt(20902) + 19968));
            sb.append((char) (AFSF.RANDOM.nextInt(20902) + 19968));
        }
        return sb.toString();
    }

    /** 快捷获取 指定 ID View（备用方案） */
    public static View getIdView(int id) {
        View view = new View(BaseApp.getI());
        view.setId(id);
        return view;
    }
}
