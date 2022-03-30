package com.cnooc.platform.datav.config.domain;
/**
 * @ClassName DVConfQuery.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 15:04:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 可视化配置查询条件
 * @author: TONG
 * @create: 2021-03-31 15:04
 **/
@Entity
@Table(name="DV_CONFIG_QUERY")
public class DVConfQuery extends BaseEntity {
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
    @Column(nullable = false)
    @RefJsonWrite
    private String input_type;
    @Column(nullable = true)
    @RefJsonWrite
    private String operator;
    @Column(nullable = true)
    @RefJsonWrite
    private String value_from;
    @Column(nullable = true)
    @RefJsonWrite
    private String value_key;

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

    public String getInput_type() {
        return input_type;
    }

    public void setInput_type(String input_type) {
        this.input_type = input_type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue_from() {
        return value_from;
    }

    public void setValue_from(String value_from) {
        this.value_from = value_from;
    }

    public String getValue_key() {
        return value_key;
    }

    public void setValue_key(String value_key) {
        this.value_key = value_key;
    }
}
