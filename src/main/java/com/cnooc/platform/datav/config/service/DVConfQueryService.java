package com.cnooc.platform.datav.config.service;/**
 * @ClassName DVConfQueryService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 15:13:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.dao.DVConfQueryDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: vels
 * @description: 查询条件服务
 * @author: TONG
 * @create: 2021-03-31 15:13
 **/
@Service
public class DVConfQueryService extends BaseService<DVConfQuery> {
    @Autowired
    private DVConfQueryDao dao;

    @Override
    public BaseDao<DVConfQuery> getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delByConf(DVConf conf){
        dao.delByConf(conf);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DVConfQuery> getByConf(DVConf conf){
        return dao.getByConf(conf);
    }
}
