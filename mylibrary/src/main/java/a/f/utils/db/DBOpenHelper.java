package a.f.utils.db;

import android.content.Context;

import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

import a.f.bean.dao.DaoMaster;
import a.f.bean.dao.LogEntityDao;
import a.f.utils.L;

/**
 * ================================================
 * 类名：a.f.utils.db
 * 时间：2021/7/20 17:30
 * 描述：  自定义升级数据库拷贝数据 帮助类
 * 升级逻辑：弃用旧表，新增高版本表，检测旧表是否有数据并拷贝到高版本表
 * <p>
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DBOpenHelper extends DaoMaster.OpenHelper {

    public DBOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        try {
            MigrationHelper.migrate(db,
                    new MigrationHelper.ReCreateAllTableListener() {
                        @Override
                        public void onCreateAllTables(Database db, boolean ifNotExists) {
                            DaoMaster.createAllTables(db, ifNotExists);
                        }

                        @Override
                        public void onDropAllTables(Database db, boolean ifExists) {
                            DaoMaster.dropAllTables(db, ifExists);
                        }
                    },
                    LogEntityDao.class
            );
        } catch (Exception e) {
            L.writeExceptionLog(e);
        }
    }

}
