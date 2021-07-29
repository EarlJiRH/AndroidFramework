package a.f.bean.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/**
 * ================================================
 * 类名：a.f.bean.dao
 * 时间：2021/7/20 16:41
 * 描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 * ================================================
 *
 * @author Admin
 */
public class LogEntityDao extends AbstractDao<LogEntity, Long> {

    public static final String TABLENAME = "LOG_ENTITY";

    /**
     * Properties of entity LogEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Key_id = new Property(1, String.class, "key_id", false, "KEY_ID");
        public final static Property Log_type = new Property(2, Integer.class, "log_type", false, "LOG_TYPE");
        public final static Property Log_content = new Property(3, String.class, "log_content", false, "LOG_CONTENT");
        public final static Property Log_record_time = new Property(4, Long.class, "log_record_time", false, "LOG_RECORD_TIME");
        public final static Property User_key = new Property(5, String.class, "user_key", false, "USER_KEY");
        public final static Property Software_name = new Property(6, String.class, "software_name", false, "SOFTWARE_NAME");
        public final static Property Software_type = new Property(7, Integer.class, "software_type", false, "SOFTWARE_TYPE");
        public final static Property Software_version_name = new Property(8, String.class, "software_version_name", false, "SOFTWARE_VERSION_NAME");
        public final static Property Software_version_code = new Property(9, Integer.class, "software_version_code", false, "SOFTWARE_VERSION_CODE");
        public final static Property Software_package_name = new Property(10, String.class, "software_package_name", false, "SOFTWARE_PACKAGE_NAME");
        public final static Property Software_channel = new Property(11, String.class, "software_channel", false, "SOFTWARE_CHANNEL");
        public final static Property System_version_name = new Property(12, String.class, "system_version_name", false, "SYSTEM_VERSION_NAME");
        public final static Property System_version_code = new Property(13, Integer.class, "system_version_code", false, "SYSTEM_VERSION_CODE");
        public final static Property Device_id = new Property(14, String.class, "device_id", false, "DEVICE_ID");
        public final static Property Device_brand = new Property(15, String.class, "device_brand", false, "DEVICE_BRAND");
        public final static Property Device_model = new Property(16, String.class, "device_model", false, "DEVICE_MODEL");
        public final static Property Device_abis = new Property(17, String.class, "device_abis", false, "DEVICE_ABIS");
        public final static Property Device_screen_width = new Property(18, Integer.class, "device_screen_width", false, "DEVICE_SCREEN_WIDTH");
        public final static Property Device_screen_height = new Property(19, Integer.class, "device_screen_height", false, "DEVICE_SCREEN_HEIGHT");
    }


    public LogEntityDao(DaoConfig config) {
        super(config);
    }

    public LogEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOG_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"KEY_ID\" TEXT," + // 1: key_id
                "\"LOG_TYPE\" INTEGER," + // 2: log_type
                "\"LOG_CONTENT\" TEXT," + // 3: log_content
                "\"LOG_RECORD_TIME\" INTEGER," + // 4: log_record_time
                "\"USER_KEY\" TEXT," + // 5: user_key
                "\"SOFTWARE_NAME\" TEXT," + // 6: software_name
                "\"SOFTWARE_TYPE\" INTEGER," + // 7: software_type
                "\"SOFTWARE_VERSION_NAME\" TEXT," + // 8: software_version_name
                "\"SOFTWARE_VERSION_CODE\" INTEGER," + // 9: software_version_code
                "\"SOFTWARE_PACKAGE_NAME\" TEXT," + // 10: software_package_name
                "\"SOFTWARE_CHANNEL\" TEXT," + // 11: software_channel
                "\"SYSTEM_VERSION_NAME\" TEXT," + // 12: system_version_name
                "\"SYSTEM_VERSION_CODE\" INTEGER," + // 13: system_version_code
                "\"DEVICE_ID\" TEXT," + // 14: device_id
                "\"DEVICE_BRAND\" TEXT," + // 15: device_brand
                "\"DEVICE_MODEL\" TEXT," + // 16: device_model
                "\"DEVICE_ABIS\" TEXT," + // 17: device_abis
                "\"DEVICE_SCREEN_WIDTH\" INTEGER," + // 18: device_screen_width
                "\"DEVICE_SCREEN_HEIGHT\" INTEGER);"); // 19: device_screen_height
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOG_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LogEntity entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String key_id = entity.getKey_id();
        if (key_id != null) {
            stmt.bindString(2, key_id);
        }

        Integer log_type = entity.getLog_type();
        if (log_type != null) {
            stmt.bindLong(3, log_type);
        }

        String log_content = entity.getLog_content();
        if (log_content != null) {
            stmt.bindString(4, log_content);
        }

        Long log_record_time = entity.getLog_record_time();
        if (log_record_time != null) {
            stmt.bindLong(5, log_record_time);
        }

        String user_key = entity.getUser_key();
        if (user_key != null) {
            stmt.bindString(6, user_key);
        }

        String software_name = entity.getSoftware_name();
        if (software_name != null) {
            stmt.bindString(7, software_name);
        }

        Integer software_type = entity.getSoftware_type();
        if (software_type != null) {
            stmt.bindLong(8, software_type);
        }

        String software_version_name = entity.getSoftware_version_name();
        if (software_version_name != null) {
            stmt.bindString(9, software_version_name);
        }

        Integer software_version_code = entity.getSoftware_version_code();
        if (software_version_code != null) {
            stmt.bindLong(10, software_version_code);
        }

        String software_package_name = entity.getSoftware_package_name();
        if (software_package_name != null) {
            stmt.bindString(11, software_package_name);
        }

        String software_channel = entity.getSoftware_channel();
        if (software_channel != null) {
            stmt.bindString(12, software_channel);
        }

        String system_version_name = entity.getSystem_version_name();
        if (system_version_name != null) {
            stmt.bindString(13, system_version_name);
        }

        Integer system_version_code = entity.getSystem_version_code();
        if (system_version_code != null) {
            stmt.bindLong(14, system_version_code);
        }

        String device_id = entity.getDevice_id();
        if (device_id != null) {
            stmt.bindString(15, device_id);
        }

        String device_brand = entity.getDevice_brand();
        if (device_brand != null) {
            stmt.bindString(16, device_brand);
        }

        String device_model = entity.getDevice_model();
        if (device_model != null) {
            stmt.bindString(17, device_model);
        }

        String device_abis = entity.getDevice_abis();
        if (device_abis != null) {
            stmt.bindString(18, device_abis);
        }

        Integer device_screen_width = entity.getDevice_screen_width();
        if (device_screen_width != null) {
            stmt.bindLong(19, device_screen_width);
        }

        Integer device_screen_height = entity.getDevice_screen_height();
        if (device_screen_height != null) {
            stmt.bindLong(20, device_screen_height);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LogEntity entity) {
        stmt.clearBindings();

        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }

        String key_id = entity.getKey_id();
        if (key_id != null) {
            stmt.bindString(2, key_id);
        }

        Integer log_type = entity.getLog_type();
        if (log_type != null) {
            stmt.bindLong(3, log_type);
        }

        String log_content = entity.getLog_content();
        if (log_content != null) {
            stmt.bindString(4, log_content);
        }

        Long log_record_time = entity.getLog_record_time();
        if (log_record_time != null) {
            stmt.bindLong(5, log_record_time);
        }

        String user_key = entity.getUser_key();
        if (user_key != null) {
            stmt.bindString(6, user_key);
        }

        String software_name = entity.getSoftware_name();
        if (software_name != null) {
            stmt.bindString(7, software_name);
        }

        Integer software_type = entity.getSoftware_type();
        if (software_type != null) {
            stmt.bindLong(8, software_type);
        }

        String software_version_name = entity.getSoftware_version_name();
        if (software_version_name != null) {
            stmt.bindString(9, software_version_name);
        }

        Integer software_version_code = entity.getSoftware_version_code();
        if (software_version_code != null) {
            stmt.bindLong(10, software_version_code);
        }

        String software_package_name = entity.getSoftware_package_name();
        if (software_package_name != null) {
            stmt.bindString(11, software_package_name);
        }

        String software_channel = entity.getSoftware_channel();
        if (software_channel != null) {
            stmt.bindString(12, software_channel);
        }

        String system_version_name = entity.getSystem_version_name();
        if (system_version_name != null) {
            stmt.bindString(13, system_version_name);
        }

        Integer system_version_code = entity.getSystem_version_code();
        if (system_version_code != null) {
            stmt.bindLong(14, system_version_code);
        }

        String device_id = entity.getDevice_id();
        if (device_id != null) {
            stmt.bindString(15, device_id);
        }

        String device_brand = entity.getDevice_brand();
        if (device_brand != null) {
            stmt.bindString(16, device_brand);
        }

        String device_model = entity.getDevice_model();
        if (device_model != null) {
            stmt.bindString(17, device_model);
        }

        String device_abis = entity.getDevice_abis();
        if (device_abis != null) {
            stmt.bindString(18, device_abis);
        }

        Integer device_screen_width = entity.getDevice_screen_width();
        if (device_screen_width != null) {
            stmt.bindLong(19, device_screen_width);
        }

        Integer device_screen_height = entity.getDevice_screen_height();
        if (device_screen_height != null) {
            stmt.bindLong(20, device_screen_height);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }

    @Override
    public LogEntity readEntity(Cursor cursor, int offset) {
        LogEntity entity = new LogEntity( //
                cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
                cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // key_id
                cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // log_type
                cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // log_content
                cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4), // log_record_time
                cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // user_key
                cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // software_name
                cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // software_type
                cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // software_version_name
                cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9), // software_version_code
                cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // software_package_name
                cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // software_channel
                cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // system_version_name
                cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13), // system_version_code
                cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // device_id
                cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // device_brand
                cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // device_model
                cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // device_abis
                cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18), // device_screen_width
                cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19) // device_screen_height
        );
        return entity;
    }

    @Override
    public void readEntity(Cursor cursor, LogEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setKey_id(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLog_type(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setLog_content(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLog_record_time(cursor.isNull(offset + 4) ? null : cursor.getLong(offset + 4));
        entity.setUser_key(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSoftware_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setSoftware_type(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setSoftware_version_name(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSoftware_version_code(cursor.isNull(offset + 9) ? null : cursor.getInt(offset + 9));
        entity.setSoftware_package_name(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSoftware_channel(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setSystem_version_name(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setSystem_version_code(cursor.isNull(offset + 13) ? null : cursor.getInt(offset + 13));
        entity.setDevice_id(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setDevice_brand(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setDevice_model(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setDevice_abis(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setDevice_screen_width(cursor.isNull(offset + 18) ? null : cursor.getInt(offset + 18));
        entity.setDevice_screen_height(cursor.isNull(offset + 19) ? null : cursor.getInt(offset + 19));
    }

    @Override
    protected final Long updateKeyAfterInsert(LogEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }

    @Override
    public Long getKey(LogEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(LogEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
}
