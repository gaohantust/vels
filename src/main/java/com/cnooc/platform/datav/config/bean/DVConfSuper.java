package com.cnooc.platform.datav.config.bean;
/**
 * @ClassName DVConfSuper.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 11:29:00
 */

import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfQuery;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化配置载体父类
 * @author: TONG
 * @create: 2021-04-09 11:29
 **/
public class DVConfSuper {
    @RefJsonWrite
    private DVConf conf;
    @RefJsonWrite
    private List<DVConfQuery> querys;

    public DVConf getConf() {
        return conf;
    }

    public void setConf(DVConf conf) {
        this.conf = conf;
    }

    public List<DVConfQuery> getQuerys() {
        return querys;
    }

    public void setQuerys(List<DVConfQuery> querys) {
        this.querys = querys;
    }
}
