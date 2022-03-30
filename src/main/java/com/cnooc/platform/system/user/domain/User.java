package com.cnooc.platform.system.user.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.cnooc.platform.core.ArchiveEntity;
import com.cnooc.platform.util.json.annotation.SelfJsonIngore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author TONG
 */
@Entity
@Table(name="SYS_USER")
public class User extends ArchiveEntity {
    @Column(nullable = false)
    @SelfJsonIngore
    private String password;
    @Column(nullable = true)
    private String email;
    @Column(nullable = true)
    private String phone;
    @Column(nullable = true)
    private String sex;
    @Column(nullable = true)
    private String type;
    private String max_password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMax_password() {
        return max_password;
    }

    public void setMax_password(String max_password) {
        this.max_password = max_password;
    }
}
