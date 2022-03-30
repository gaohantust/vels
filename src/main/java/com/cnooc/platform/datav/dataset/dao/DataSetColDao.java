package com.cnooc.platform.datav.dataset.dao;/**
 * @ClassName DataSetColDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月30日 09:59:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.datav.dataset.domain.DataSetCol;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: DataSetColDao
 * @author: TONG
 * @create: 2021-03-30 09:59
 **/
@Component
public class DataSetColDao extends BaseDao<DataSetCol> {
    public List<DataSetCol> getByDataSet(DataSet dataSet){
        String jpql="from DataSetCol where dataSet=:dataset";
        Map<String,Object> param=new HashMap<>();
        param.put("dataset",dataSet);
        return findAll(jpql,param);
    }
    public void delByDataSet(DataSet dataSet){
        String jpql="delete from DataSetCol where dataSet=:dataset ";
        Map<String,Object> param=new HashMap<>();
        param.put("dataset",dataSet);
        batchDelete(jpql,param);
    }
}
