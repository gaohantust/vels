package com.cnooc.platform.datav.config.domain;/**
 * @ClassName DVConfLine.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:15:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 可视化配置折线
 * @author: TONG
 * @create: 2021-04-09 15:15
 **/
@Entity
@Table(name="DV_CONFIG_LINE")
public class DVConfLine extends BaseEntity {
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
    private String type;
    /**
     *数据轴类型 Y:y轴；T:类别轴
     * @author TONG
     * @date 2021/4/22 13:30
     * @return
     */
    @Column(nullable = false)
    @RefJsonWrite
    private String data_type;
    /**
     *Y轴列
     * @author TONG
     * @date 2021/4/22 13:30
     * @return
     */
    @Column(nullable = false)
    @RefJsonWrite
    private String y_col;
    @Column(nullable = true)
    @RefJsonWrite
    private String color;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getY_col() {
        return y_col;
    }

    public void setY_col(String y_col) {
        this.y_col = y_col;
    }
}
