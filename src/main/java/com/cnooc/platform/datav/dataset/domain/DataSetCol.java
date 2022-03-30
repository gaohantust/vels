package com.cnooc.platform.datav.dataset.domain;
/**
 * @ClassName DataSetCol.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月26日 08:45:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.role.domain.Role;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 数据集列信息
 * @author: TONG
 * @create: 2021-03-26 08:45
 **/
@Entity
@Table(name="DV_DATASET_COL")
public class DataSetCol extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="dataSet",nullable=false)
    private DataSet dataSet;
    @Column(nullable = false)
    private String col;
    @Column(nullable = false)
    private String col_zh;
    @Column(nullable = false)
    private String data_type;
    @Column(nullable = true)
    private String remark;

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
