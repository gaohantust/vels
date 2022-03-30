package com.cnooc.platform.datav.config.bean;
/**
 * @ClassName DVConfPieBean.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 11:31:00
 */

import com.cnooc.platform.datav.config.domain.DVConfPie;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

/**
 * @program: vels
 * @description: 可视化配置饼图Bean
 * @author: TONG
 * @create: 2021-04-09 11:31
 **/
public class DVConfPieBean extends DVConfSuper{
    @RefJsonWrite
    private DVConfPie pie;

    public DVConfPie getPie() {
        return pie;
    }

    public void setPie(DVConfPie pie) {
        this.pie = pie;
    }
}
