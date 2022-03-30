package com.cnooc.platform.datav.dataset.domain;
/**
 * @ClassName DataSet.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月26日 08:43:00
 */

import com.cnooc.platform.core.ArchiveEntity;
import com.cnooc.platform.system.user.domain.User;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 数据集
 * @author: TONG
 * @create: 2021-03-26 08:43
 **/
@Entity
@Table(name="DV_DATASET")
public class DataSet extends ArchiveEntity {
    @Column(nullable = false)
    private String em_key;
    @Column(nullable = false)
    private String sql;
    @Column(nullable = true)
    private int steps = 0;
    @ManyToOne
    @JoinColumn(name="sys_user",nullable=true)
    private User sys_user;

    public String getEm_key() {
        return em_key;
    }

    public void setEm_key(String em_key) {
        this.em_key = em_key;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public User getSys_user() {
        return sys_user;
    }

    public void setSys_user(User sys_user) {
        this.sys_user = sys_user;
    }
}
