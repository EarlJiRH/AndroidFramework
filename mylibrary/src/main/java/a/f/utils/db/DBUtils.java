package a.f.utils.db;

import a.f.base.BaseApp;
import a.f.bean.dao.DaoMaster;
import a.f.bean.dao.DaoSession;
import a.f.utils.constant.AFSF;

/**
 * ================================================
 * 类名：a.f.utils.db
 * 时间：2021/7/20 17:31
 * 描述：数据库 工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DBUtils {

    private static DaoSession mDaoSession;

    /** 获取 DaoSession 的实例 */
    public synchronized static DaoSession getDaoSession() {
        if (mDaoSession == null) {
            DBOpenHelper dbOpenHelper = new DBOpenHelper(BaseApp.getI(), AFSF.FILE_APPLICATION_ID$DB_AF);
            DaoMaster daoMaster = new DaoMaster(dbOpenHelper.getWritableDatabase());
            mDaoSession = daoMaster.newSession();
        }
        return mDaoSession;
    }
}
