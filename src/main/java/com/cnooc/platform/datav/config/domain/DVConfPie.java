package com.cnooc.platform.datav.config.domain;/**
 * @ClassName DVConfPie.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:22:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 可视化配置饼图配置
 * @author: TONG
 * @create: 2021-04-09 15:22
 **/
@Table(name="DV_CONFIG_PIE")
@Entity
public class DVConfPie extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="conf",nullable=false)
    private DVConf conf;
    @Column(nullable = false)
    @RefJsonWrite
    private String col_label;
    @Column(nullable = false)
    @RefJsonWrite
    private String col_value;

    public DVConf getConf() {
        return conf;
    }

    public void setConf(DVConf conf) {
        this.conf = conf;
    }

    public String getCol_label() {
        return col_label;
    }

    public void setCol_label(String col_label) {
        this.col_label = col_label;
    }

    public String getCol_value() {
        return col_value;
    }

    public void setCol_value(String col_value) {
        this.col_value = col_value;
    }
}
