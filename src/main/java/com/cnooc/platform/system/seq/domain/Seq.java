package com.cnooc.platform.system.seq.domain;

import com.cnooc.platform.core.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 11:35
 */
@Entity
@Table(name="SYS_SEQ")
public class Seq extends BaseEntity {
    @Id
    @Column(nullable = true)
    private String id;
    @Column(nullable = false)
    private String table_name;
    @Column(nullable = true)
    private String prefix;
    @Column(nullable = true)
    private String prefix_strategy;
    @Column(nullable = false)
    private long curr_value;
    @Column(nullable = false)
    private int inc;
    @Column(nullable = true)
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix_strategy() {
        return prefix_strategy;
    }

    public void setPrefix_strategy(String prefix_strategy) {
        this.prefix_strategy = prefix_strategy;
    }

    public long getCurr_value() {
        return curr_value;
    }

    public void setCurr_value(long curr_value) {
        this.curr_value = curr_value;
    }

    public int getInc() {
        return inc;
    }

    public void setInc(int inc) {
        this.inc = inc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
