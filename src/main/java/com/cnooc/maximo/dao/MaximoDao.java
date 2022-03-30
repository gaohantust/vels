package com.cnooc.maximo.dao;
/**
 * @ClassName MaximoDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月18日 09:57:00
 */

import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.util.StringUtils;
import com.cnooc.platform.util.system.QueryConditionTransformTuple;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: Maximo数据访问类
 * @author: TONG
 * @create: 2021-03-18 09:57
 **/
@Component
public class MaximoDao {
    @PersistenceContext(unitName = "maximoPersistenceUnit")
    @Autowired
    @Qualifier(value = "emMaximo")
    protected EntityManager em;
    public void flush() {
        em.flush();
    }
    /**
     * @param
     * @return List<Map < String, Object>>
     * @throws
     * @Title: findBySql
     * @Description:根据SQL查询
     * @author CST-TONGLZ
     */
    public List<Map<String, Object>> findBySql(String sql, Map<String, Object> paraMap) {
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        if (paraMap != null) {
            for (String key : paraMap.keySet()) {
                query.setParameter(key, paraMap.get(key));
            }
        }
        return query.getResultList();
    }

    /**
     * @param
     * @return List<Map < String, Object>>
     * @throws
     * @Title: findBySql
     * @Description:根据SQL查询
     * @author CST-TONGLZ
     */
    public List<Map<String, Object>> findBySql(String sql) {
        return findBySql(sql, null);
    }

    public int executeUpdateSql(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.executeUpdate();
    }
    /**
     * @Title: findPage
     * @Description:获取分页数据，以Page数据格式返回
     * @param
     * @author CST-TONGLZ
     * @return Page<Map<String,Object>>
     * @throws
     */
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        if (condition == null)
            throw new RuntimeException("查询器不能为空");
        String sql = condition.getSQL();
        if (StringUtils.isEmpty(sql))
            throw new RuntimeException("SQL不能为空");

        long total = 0;
        int totalPages = 0;
        if (condition.getPageSize() > 0) {
            total = count4Condition(condition);
            totalPages = (int) (total + condition.getPageSize() - 1) / condition.getPageSize();
            if (totalPages < condition.getCurrentPage()) {
                condition.setCurrentPage(totalPages);
            }
        }
        List<Map<String, Object>> list = findListByCondition(condition);
        return new Page<Map<String, Object>>(list, total, condition);
    }
    @SuppressWarnings("unchecked")
    private int count4Condition(QueryCondition condition) {
        Object[] para = condition.getSqlInfo(false);
        String sql = (String) para[0];
        sql = condition.getCountSql(sql);
        Map<String, Object> paraMap = (Map<String, Object>) para[1];
        Query query = em.createNativeQuery(sql);
        if (paraMap != null) {
            for (String key : paraMap.keySet())
                query.setParameter(key, paraMap.get(key));
        }
        Object result = query.getSingleResult();
        if (result instanceof BigDecimal)
            return ((BigDecimal) result).intValue();
        if (result instanceof BigInteger)
            return ((BigInteger) result).intValue();
        return (Integer) result;
    }
    /**
     * @Title: findListBySql
     * @Description:执行SQL查询
     * @param
     * @author CST-TONGLZ
     * @return List<Map<String,Object>>
     * @throws
     */
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> findListByCondition(QueryCondition condition) {
        Object[] para = condition.getSqlInfo();
        String sql = (String) para[0];
        Map<String, Object> paraMap = (Map<String, Object>) para[1];
        Query query = em.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        if (paraMap != null) {
            for (String key : paraMap.keySet())
                query.setParameter(key, paraMap.get(key));
        }
        int currentPage = condition.getCurrentPage();
        int pageSize = condition.getPageSize();
        if (currentPage > 0 && pageSize > 0)
            query.setFirstResult(pageSize * (currentPage - 1)).setMaxResults(pageSize);
        return query.getResultList();
    }
}
