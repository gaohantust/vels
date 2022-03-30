package com.cnooc.platform.datav.config.service;/**
 * @ClassName DVConfPieService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年04月09日 15:26:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.dao.DVConfPieDao;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfLine;
import com.cnooc.platform.datav.config.domain.DVConfPie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: vels
 * @description: 可视化配置饼图服务
 * @author: TONG
 * @create: 2021-04-09 15:26
 **/
@Service
public class DVConfPieService extends BaseService<DVConfPie> {
    @Autowired
    private DVConfPieDao dao;

    @Override
    public BaseDao<DVConfPie> getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delByConf(DVConf conf){
        dao.delByConf(conf);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public DVConfPie getByConf(DVConf conf){
        List<DVConfPie> pies = dao.getByConf(conf);
        if(pies.size()>0){
            return pies.get(0);
        }
        return null;
    }
}
