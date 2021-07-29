package a.f.bean.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import a.f.base.BaseBean;

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
@Entity
public class LogEntity extends BaseBean {

    @Id
    private Long id;
    private String key_id;
    private Integer log_type;
    private String log_content;
    private Long log_record_time;
    private String user_key;
    private String software_name;
    private Integer software_type;
    private String software_version_name;
    private Integer software_version_code;
    private String software_package_name;
    private String software_channel;
    private String system_version_name;
    private Integer system_version_code;
    private String device_id;
    private String device_brand;
    private String device_model;
    private String device_abis;
    private Integer device_screen_width;
    private Integer device_screen_height;
    @Generated(hash = 868913465)
    public LogEntity(Long id, String key_id, Integer log_type, String log_content,
                     Long log_record_time, String user_key, String software_name,
                     Integer software_type, String software_version_name,
                     Integer software_version_code, String software_package_name,
                     String software_channel, String system_version_name,
                     Integer system_version_code, String device_id, String device_brand,
                     String device_model, String device_abis, Integer device_screen_width,
                     Integer device_screen_height) {
        this.id = id;
        this.key_id = key_id;
        this.log_type = log_type;
        this.log_content = log_content;
        this.log_record_time = log_record_time;
        this.user_key = user_key;
        this.software_name = software_name;
        this.software_type = software_type;
        this.software_version_name = software_version_name;
        this.software_version_code = software_version_code;
        this.software_package_name = software_package_name;
        this.software_channel = software_channel;
        this.system_version_name = system_version_name;
        this.system_version_code = system_version_code;
        this.device_id = device_id;
        this.device_brand = device_brand;
        this.device_model = device_model;
        this.device_abis = device_abis;
        this.device_screen_width = device_screen_width;
        this.device_screen_height = device_screen_height;
    }
    @Generated(hash = 1472642729)
    public LogEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey_id() {
        return this.key_id;
    }
    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }
    public Integer getLog_type() {
        return this.log_type;
    }
    public void setLog_type(Integer log_type) {
        this.log_type = log_type;
    }
    public String getLog_content() {
        return this.log_content;
    }
    public void setLog_content(String log_content) {
        this.log_content = log_content;
    }
    public Long getLog_record_time() {
        return this.log_record_time;
    }
    public void setLog_record_time(Long log_record_time) {
        this.log_record_time = log_record_time;
    }
    public String getUser_key() {
        return this.user_key;
    }
    public void setUser_key(String user_key) {
        this.user_key = user_key;
    }
    public String getSoftware_name() {
        return this.software_name;
    }
    public void setSoftware_name(String software_name) {
        this.software_name = software_name;
    }
    public Integer getSoftware_type() {
        return this.software_type;
    }
    public void setSoftware_type(Integer software_type) {
        this.software_type = software_type;
    }
    public String getSoftware_version_name() {
        return this.software_version_name;
    }
    public void setSoftware_version_name(String software_version_name) {
        this.software_version_name = software_version_name;
    }
    public Integer getSoftware_version_code() {
        return this.software_version_code;
    }
    public void setSoftware_version_code(Integer software_version_code) {
        this.software_version_code = software_version_code;
    }
    public String getSoftware_package_name() {
        return this.software_package_name;
    }
    public void setSoftware_package_name(String software_package_name) {
        this.software_package_name = software_package_name;
    }
    public String getSoftware_channel() {
        return this.software_channel;
    }
    public void setSoftware_channel(String software_channel) {
        this.software_channel = software_channel;
    }
    public String getSystem_version_name() {
        return this.system_version_name;
    }
    public void setSystem_version_name(String system_version_name) {
        this.system_version_name = system_version_name;
    }
    public Integer getSystem_version_code() {
        return this.system_version_code;
    }
    public void setSystem_version_code(Integer system_version_code) {
        this.system_version_code = system_version_code;
    }
    public String getDevice_id() {
        return this.device_id;
    }
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
    public String getDevice_brand() {
        return this.device_brand;
    }
    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }
    public String getDevice_model() {
        return this.device_model;
    }
    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }
    public String getDevice_abis() {
        return this.device_abis;
    }
    public void setDevice_abis(String device_abis) {
        this.device_abis = device_abis;
    }
    public Integer getDevice_screen_width() {
        return this.device_screen_width;
    }
    public void setDevice_screen_width(Integer device_screen_width) {
        this.device_screen_width = device_screen_width;
    }
    public Integer getDevice_screen_height() {
        return this.device_screen_height;
    }
    public void setDevice_screen_height(Integer device_screen_height) {
        this.device_screen_height = device_screen_height;
    }
}
