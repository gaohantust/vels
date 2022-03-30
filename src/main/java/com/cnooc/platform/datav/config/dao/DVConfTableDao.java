package com.cnooc.platform.datav.config.dao;/**
 * @ClassName DVConfTableDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 10:26:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfQuery;
import com.cnooc.platform.datav.config.domain.DVConfTable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 可视化数据列表Dao
 * @author: TONG
 * @create: 2021-04-09 10:26
 **/
@Component
public class DVConfTableDao extends BaseDao<DVConfTable> {
    public void delByConf(DVConf conf){
        String jqpl="delete from DVConfTable where conf=:conf";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        super.batchUpdate(jqpl,parm);
    }
    public List<DVConfTable> getByConf(DVConf conf){
        String jqpl="from DVConfTable where conf=:conf order by sort_no ";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        return findAll(jqpl,parm);
    }
}
