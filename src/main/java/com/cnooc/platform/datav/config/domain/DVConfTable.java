package com.cnooc.platform.datav.config.domain;
/**
 * @ClassName DVConfTable.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 10:22:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 可视化数据列表配置
 * @author: TONG
 * @create: 2021-04-09 10:22
 **/
@Entity
@Table(name="DV_CONFIG_TABLE")
public class DVConfTable extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="conf",nullable=false)
    private DVConf conf;
    @Column(nullable = false)
    @RefJsonWrite
    private String col;
    @Column(nullable = false)
    @RefJsonWrite
    private String col_zh;
    @Column(nullable = false)
    @RefJsonWrite
    private String data_type;
    @Column(nullable = true)
    @org.hibernate.annotations.Type(type = "yes_no")
    @RefJsonWrite
    private Boolean is_dic = false;
    @Column(nullable = true)
    @RefJsonWrite
    private String dic_name;
    private int sort_no;

    public DVConf getConf() {
        return conf;
    }

    public void setConf(DVConf conf) {
        this.conf = conf;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getCol_zh() {
        return col_zh;
    }

    public void setCol_zh(String col_zh) {
        this.col_zh = col_zh;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public Boolean getIs_dic() {
        return is_dic;
    }

    public void setIs_dic(Boolean is_dic) {
        this.is_dic = is_dic;
    }

    public String getDic_name() {
        return dic_name;
    }

    public void setDic_name(String dic_name) {
        this.dic_name = dic_name;
    }

    public int getSort_no() {
        return sort_no;
    }

    public void setSort_no(int sort_no) {
        this.sort_no = sort_no;
    }
}
