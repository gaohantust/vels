package com.cnooc.platform.datav.config.service;
/**
 * @ClassName DVConfLineService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:18:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.dao.DVConfLineDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfLine;
import com.cnooc.platform.datav.config.domain.DVConfTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化配置折线服务
 * @author: TONG
 * @create: 2021-04-09 15:18
 **/
@Service
public class DVConfLineService extends BaseService<DVConfLine> {
    @Autowired
    private DVConfLineDao dao;

    @Override
    public BaseDao<DVConfLine> getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delByConf(DVConf conf){
        dao.delByConf(conf);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DVConfLine> getByConf(DVConf conf){
        return dao.getByConf(conf);
    }
}
