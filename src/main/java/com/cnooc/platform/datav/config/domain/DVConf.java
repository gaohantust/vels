package com.cnooc.platform.datav.config.domain;
/**
 * @ClassName DVConf.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 09:37:00
 */

import com.cnooc.platform.core.ArchiveEntity;
import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 可视化配置主信息
 * @author: TONG
 * @create: 2021-03-31 09:37
 **/
@Entity
@Table(name="DV_CONFIG")
public class DVConf extends ArchiveEntity {
    @ManyToOne
    @JoinColumn(name="sys_user",nullable=true)
    private User sys_user;
    @ManyToOne
    @JoinColumn(name="dataset",nullable=true)
    @RefJsonWrite
    private DataSet dataSet;
    @Column(nullable = false)
    @RefJsonWrite
    private String type;
    @Column(nullable = true)
    @RefJsonWrite
    private Integer status = 0;
    @Column(nullable = true)
    @org.hibernate.annotations.Type(type = "yes_no")
    @RefJsonWrite
    private boolean page=false;
    public User getSys_user() {
        return sys_user;
    }
    //折线图X
    @Column(nullable = true)
    @RefJsonWrite
    private String x_col;
    @Column(nullable = true)
    @RefJsonWrite
    private String res_id;

    public void setSys_user(User sys_user) {
        this.sys_user = sys_user;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isPage() {
        return page;
    }

    public void setPage(boolean page) {
        this.page = page;
    }

    public String getX_col() {
        return x_col;
    }

    public void setX_col(String x_col) {
        this.x_col = x_col;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }
}
