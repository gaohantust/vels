package com.cnooc.platform.datav.config.service;
/**
 * @ClassName DVConfTableService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 10:27:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.dao.DVConfTableDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfQuery;
import com.cnooc.platform.datav.config.domain.DVConfTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化数据列表配置服务
 * @author: TONG
 * @create: 2021-04-09 10:27
 **/
@Service
public class DVConfTableService extends BaseService<DVConfTable> {
    @Autowired
    private DVConfTableDao dao;

    @Override
    public BaseDao<DVConfTable> getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delByConf(DVConf conf){
        dao.delByConf(conf);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DVConfTable> getByConf(DVConf conf){
        return dao.getByConf(conf);
    }
}
