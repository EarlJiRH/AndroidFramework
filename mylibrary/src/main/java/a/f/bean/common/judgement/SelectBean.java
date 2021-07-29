package a.f.bean.common.judgement;

import a.f.base.BaseBean;

/**
 * ================================================
 * 类名：a.f.bean.common.judgement
 * 时间：2021/7/20 16:37
 * 描述：选择/提示 Item Bean
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SelectBean extends BaseBean {

    protected int numberI; // 编号 整型
    protected String numberS; // 编号 字符串类型
    protected String title; // 标题
    protected String imageUrl; // 网址图片
    protected int imageR; // 资源文件的图片
    protected Object object; // 附件对象
    protected boolean isChecked; // 是否选中

    public SelectBean(int numberI, String title) {
        this.numberI = numberI;
        this.title = title;
    }

    public SelectBean(int numberI, String title, int imageR) {
        this.numberI = numberI;
        this.title = title;
        this.imageR = imageR;
    }

    public SelectBean(int numberI, String title, boolean isChecked) {
        this.numberI = numberI;
        this.title = title;
        this.isChecked = isChecked;
    }

    public SelectBean(int numberI, String title, Object object) {
        this.numberI = numberI;
        this.title = title;
        this.object = object;
    }

    public SelectBean(String title, Object object) {
        this.title = title;
        this.object = object;
    }

    public int getNumberI() {
        return numberI;
    }

    public void setNumberI(int numberI) {
        this.numberI = numberI;
    }

    public String getNumberS() {
        return numberS;
    }

    public void setNumberS(String numberS) {
        this.numberS = numberS;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getImageR() {
        return imageR;
    }

    public void setImageR(int imageR) {
        this.imageR = imageR;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
