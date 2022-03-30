package com.cnooc.platform.datav.dataset.dao;/**
 * @ClassName DataSetDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月26日 11:43:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.datav.dataset.domain.DataSetCol;
import com.cnooc.platform.exception.bean.ServiceException;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.util.system.QueryConditionTransformTuple;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 数据集Dao
 * @author: TONG
 * @create: 2021-03-26 11:43
 **/
@Component
public class DataSetDao extends BaseDao<DataSet> {
    @PersistenceContext(unitName = "primaryPersistenceUnit")
    @Autowired
    @Qualifier(value = "emPrimary")
    protected EntityManager primaryEM;
    @PersistenceContext(unitName = "maximoPersistenceUnit")
    @Autowired
    @Qualifier(value = "emMaximo")
    protected EntityManager maximoEM;

    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String userid = getCurrentUser().getId();
        String sql = "select id,code,name,em_key,valid from DV_DATASET where sys_user='" + userid + "'";
        condition.setSQL(sql);
        condition.addDicReplace("datasource", "em_key", "em_key_name");
        return super.findPage(condition);
    }

    /**
     * 验证SQL
     *
     * @param ds
     * @return void
     * @author TONG
     * @date 2021/3/30 9:02
     */
    public void checkSQL(DataSet ds) {
        String datasource = ds.getEm_key();
        EntityManager em = getEM(datasource);
        Query query = em.createNativeQuery(ds.getSql());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        query.setFirstResult(0).setMaxResults(1);
        query.getResultList();
    }

    public List<DataSetCol> getDataSetMeta(DataSet ds) {
        String datasource = ds.getEm_key();
        EntityManager em = getEM(datasource);
        Query query = em.createNativeQuery(ds.getSql());
        query.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        query.setFirstResult(0).setMaxResults(1);
        List<Map<String, Object>> list = query.getResultList();
        if (list.size() == 0) {
            throw new ServiceException("sql", "当前数据集没有数据");
        }
        List<DataSetCol> cs = new ArrayList<>();
        Map<String, Object> map = list.get(0);
        for (String key : map.keySet()) {
            DataSetCol c = new DataSetCol();
            c.setCol(key);
            c.setCol_zh(key);
            c.setData_type("string");
            cs.add(c);
        }
        return cs;
    }

    private EntityManager getEM(String datasource) {
        if ("emPrimary".equals(datasource)) {
            return primaryEM;
        } else if ("emMaximo".equals(datasource)) {
            return maximoEM;
        } else {
            return primaryEM;
        }
    }
    /**
     *获取分页数据
     * @param dataSet
     * @param condition
     * @author TONG
     * @date 2021/4/14 9:38 
     * @return com.cnooc.platform.page.Page
     */
    public Page getPageData(DataSet dataSet, QueryCondition condition) {
        String datasource = dataSet.getEm_key();
        EntityManager em = getEM(datasource);
        condition.setSQL(dataSet.getSql());
        return super.findPage(condition, em);
    }
    /**
     *获取所有数据
     * @author TONG
     * @date 2021/4/14 9:39
     * @return com.cnooc.platform.page.Page
     */
    public Page getData(DataSet dataSet, QueryCondition condition) {
        String datasource = dataSet.getEm_key();
        EntityManager em = getEM(datasource);
        condition.setSQL(dataSet.getSql());
        condition.setPageSize(-1);
        return super.findPage(condition, em);
    }
}
