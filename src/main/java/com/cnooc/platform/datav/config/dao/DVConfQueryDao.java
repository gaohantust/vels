package com.cnooc.platform.datav.config.dao;/**
 * @ClassName DVConfQueryDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 15:13:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfQuery;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 查询条件Dao
 * @author: TONG
 * @create: 2021-03-31 15:13
 **/
@Component
public class DVConfQueryDao extends BaseDao<DVConfQuery> {
    public void delByConf(DVConf conf){
        String jqpl="delete from DVConfQuery where conf=:conf";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        super.batchUpdate(jqpl,parm);
    }
    public List<DVConfQuery> getByConf(DVConf conf){
        String jqpl="from DVConfQuery where conf=:conf";
        Map<String,Object> parm=new HashMap<>();
        parm.put("conf",conf);
        return findAll(jqpl,parm);
    }
}
