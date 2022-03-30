package com.cnooc.platform.datav.config.dao;/**
 * @ClassName DVConfLineDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:17:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfLine;
import com.cnooc.platform.datav.config.domain.DVConfPie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 可视化配置折线Dao
 * @author: TONG
 * @create: 2021-04-09 15:17
 **/
@Component
public class DVConfLineDao extends BaseDao<DVConfLine> {
    public void delByConf(DVConf conf){
        String jqpl="delete from DVConfLine where conf=:conf";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        super.batchUpdate(jqpl,parm);
    }
    public List<DVConfLine> getByConf(DVConf conf){
        String jqpl="from DVConfLine where conf=:conf";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        return findAll(jqpl,parm);
    }
}
