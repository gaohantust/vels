package com.cnooc.platform.datav.config.bean;/**
 * @ClassName DVConfTableBean.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 11:30:00
 */

import com.cnooc.platform.datav.config.domain.DVConfTable;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化配置数据列表Bean
 * @author: TONG
 * @create: 2021-04-09 11:30
 **/
public class DVConfTableBean extends DVConfSuper {
    private List<DVConfTable> tables;

    public List<DVConfTable> getTables() {
        return tables;
    }

    public void setTables(List<DVConfTable> tables) {
        this.tables = tables;
    }
}
