package com.cnooc.platform.datav.config.bean;/**
 * @ClassName DVConfLineBean.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:19:00
 */

import com.cnooc.platform.datav.config.domain.DVConfLine;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化配置LineBean
 * @author: TONG
 * @create: 2021-04-09 15:19
 **/
public class DVConfLineBean extends DVConfSuper{
    private List<DVConfLine> lines;

    public List<DVConfLine> getLines() {
        return lines;
    }

    public void setLines(List<DVConfLine> lines) {
        this.lines = lines;
    }
}
