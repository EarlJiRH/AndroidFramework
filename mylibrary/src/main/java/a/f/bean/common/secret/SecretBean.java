package a.f.bean.common.secret;

import a.f.base.BaseBean;

/**
 * ================================================
 * 类名：a.f.bean.common.secret
 * 时间：2021/7/20 16:39
 * 描述：接口数据加解密 Bean
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class SecretBean extends BaseBean {

    private String action; // 具体 接口/功能/模块
    private String sign; // 16个字符组成
    private String data; // 加密后的数据

    public SecretBean(String action) {
        this.action = action;
    }

    public SecretBean(String action, String sign, String data) {
        this.action = action;
        this.sign = sign;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
