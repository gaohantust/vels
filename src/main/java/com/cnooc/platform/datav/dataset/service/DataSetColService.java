package com.cnooc.platform.datav.dataset.service;/**
 * @ClassName DataSetColService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月30日 09:59:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.dataset.dao.DataSetColDao;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.datav.dataset.domain.DataSetCol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: vels
 * @description: DataSetCol服务
 * @author: TONG
 * @create: 2021-03-30 09:59
 **/
@Service
public class DataSetColService extends BaseService<DataSetCol> {
    @Autowired
    private DataSetColDao dao;

    @Override
    public BaseDao<DataSetCol> getDao() {
        return dao;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<DataSetCol> getByDataSet(DataSet dataSet){
        return dao.getByDataSet(dataSet);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void delByDataSet(DataSet dataSet){
        dao.delByDataSet(dataSet);
    }
}
