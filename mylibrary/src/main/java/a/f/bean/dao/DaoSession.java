package a.f.bean.dao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

/**
 * ================================================
 * 类名：a.f.bean.dao
 * 时间：2021/7/20 16:40
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig logEntityDaoConfig;

    private final LogEntityDao logEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        logEntityDaoConfig = daoConfigMap.get(LogEntityDao.class).clone();
        logEntityDaoConfig.initIdentityScope(type);

        logEntityDao = new LogEntityDao(logEntityDaoConfig, this);

        registerDao(LogEntity.class, logEntityDao);
    }

    public void clear() {
        logEntityDaoConfig.clearIdentityScope();
    }

    public LogEntityDao getLogEntityDao() {
        return logEntityDao;
    }
}
