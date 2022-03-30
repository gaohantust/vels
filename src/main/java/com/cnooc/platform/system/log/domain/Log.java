package com.cnooc.platform.system.log.domain;/**
 * @ClassName Log.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月30日 13:58:00
 */

import com.cnooc.platform.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: vels
 * @description: 日志实体
 * @author: TONG
 * @create: 2020-12-30 13:58
 **/
@Entity
@Table(name = "SYS_LOG")
public class Log extends BaseEntity {
    @Column(nullable = false)
    private String user_code;
    @Column(nullable = false)
    private String user_name;
    @Column(nullable = true)
    private String res_code;
    @Column(nullable = true)
    private String res_name;
    @Column(nullable = true)
    private String ip;
    @Column(nullable = true)
    private String des;
    @Column(nullable = false)
    private String url;
    @Column(length=3600,nullable = true)
    private String param;
    @Column(nullable = true)
    private String browser;
    @Column(nullable = true)
    private String os;
    @Column(nullable = true)
    private long times;
    @Column(nullable = true)
    private String method;

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRes_code() {
        return res_code;
    }

    public void setRes_code(String res_code) {
        this.res_code = res_code;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
