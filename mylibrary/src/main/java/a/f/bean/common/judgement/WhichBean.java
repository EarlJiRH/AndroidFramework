package a.f.bean.common.judgement;

import java.util.List;
import java.util.Map;

import a.f.base.BaseBean;

/**
 * ================================================
 * 类名：a.f.bean.common.judgement
 * 时间：2021/7/20 16:38
 * 描述：公共Bean 用于判断case 并能带参数
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class WhichBean extends BaseBean {

    public int whichI; // case int 类型
    public long whichL; // case long 类型
    public double whichD; // case double 类型
    public boolean whichB; // case boolean 类型
    protected Object obj;
    protected List<Object> list;
    protected Map<String, Object> map;

    public WhichBean(int whichI) {
        super();
        this.whichI = whichI;
    }

    public WhichBean(int whichI, Object obj) {
        super();
        this.whichI = whichI;
        this.obj = obj;
    }

    public WhichBean(long whichL, Object obj) {
        super();
        this.whichL = whichL;
        this.obj = obj;
    }

    public WhichBean(double whichD, Object obj) {
        super();
        this.whichD = whichD;
        this.obj = obj;
    }

    public WhichBean(boolean whichB, Object obj) {
        super();
        this.whichB = whichB;
        this.obj = obj;
    }

    public WhichBean(int whichI, List<Object> list) {
        super();
        this.whichI = whichI;
        this.list = list;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
