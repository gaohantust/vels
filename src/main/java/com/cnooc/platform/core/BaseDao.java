package com.cnooc.platform.core;

import com.cnooc.platform.exception.bean.LoginException;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.seq.service.SeqService;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.Global;
import com.cnooc.platform.util.StringUtils;
import com.cnooc.platform.util.system.QueryConditionTransformTuple;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author LZ.T
 * @version V2.0
 * @ClassName: BaseDao
 * @Description: TODO 持久层基类，实现了基础的CRUD方法
 * @date 2016-11-26 下午11:48:47
 */
public class BaseDao<T extends BaseEntity> {
    private Class<T> entityClass;
    @PersistenceContext(unitName = "primaryPersistenceUnit")
    @Autowired
    @Qualifier(value = "emPrimary")
    protected EntityManager em;
    @Autowired
    private SeqService seqService;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        Type type = getClass().getGenericSuperclass();
        Type[] paraTypes = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>) paraTypes[0];
    }

    /**
     * @Title: flush @Description: TODO 刷新EM预执行操作 @param @return void @author
     * LZ.T @throws
     */
    public void flush() {
        em.flush();
    }

    /**
     * @Title: create @Description:持久化Entity @param @author CST-TONGLZ @return
     * void @throws
     */
    public void create(T entity) {
        entity.setId(seqService.getSeqValue(entity.getClass().getSimpleName()));
        em.persist(entity);
    }

    /**
     * @Title: findByID @Description:根据主键查询 @param @author CST-TONGLZ @return
     * T @throws
     */
    public T findByID(Serializable entityID) {
        if (entityID == null) {
            throw new RuntimeException(entityClass.getName() + ":id 不允许为空");
        }
        return em.find(entityClass, entityID);
    }

    /**
     * @Title: update @Description:更新Entity @param @author CST-TONGLZ @return
     * void @throws
     */
    public T update(T entity) {
        return em.merge(entity);
    }

    /**
     * @Title: delete @Description:单个实体删除 @param @author CST-TONGLZ @return
     * void @throws
     */
    public void delete(Serializable entityID) {
        em.remove(em.getReference(entityClass, entityID));
    }

    /**
     * @Title: delete @Description:多行删除 @param @author CST-TONGLZ @return
     * void @throws
     */
    public void batchDelete(Serializable[] entityids) {
        for (Serializable id : entityids) {
            em.remove(em.getReference(entityClass, id));
        }
    }

    /**
     *
     * @param fieldName
     * @param fieldValue
     * @author TONG
     * @date 2020/12/14 20:50
     * @return T
     */
    public T findByField(String fieldName, Object fieldValue) {
        if (fieldValue == null)
            return null;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), fieldValue));
        criteriaQuery.where(condition);
        List<T> list = find(criteriaQuery);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @Title: findByCode @Description:根据fieldName查询 @param @author
     * CST-TONGLZ @return void @throws
     */
    public T findByField(String fieldName, Object fieldValue, Serializable id) {
        if (fieldValue == null)
            return null;
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get(fieldName), fieldValue),
                criteriaBuilder.and(criteriaBuilder.notEqual(root.get("id"), id)));
        criteriaQuery.where(condition);
        List<T> list = find(criteriaQuery);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @Title: findByCode @Description:根据Code查询 @param @author CST-TONGLZ @return
     * void @throws
     */
    public T findByCode(Serializable code) {
        if (code == null)
            throw new RuntimeException(entityClass.getName() + ":code不允许为空");
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get("code"), code));
        criteriaQuery.where(condition);
        List<T> list = find(criteriaQuery);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @Title: findByCode @Description:根据Code查询 @param @author CST-TONGLZ @return
     * void @throws
     */
    public T findByCode(Serializable code, Serializable id) {
        if (code == null)
            throw new RuntimeException(entityClass.getName() + ":code不允许为空");
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Predicate condition = criteriaBuilder.and(criteriaBuilder.equal(root.get("code"), code),
                criteriaBuilder.and(criteriaBuilder.notEqual(root.get("id"), id)));
        criteriaQuery.where(condition);
        List<T> list = find(criteriaQuery);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @Title: findAll @Description:查询T的所有数据 @param @author CST-TONGLZ @return
     * List<T> @throws
     */
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        return find(criteriaQuery);
    }

    /**
     * @Title: find @Description:执行查询 @param @author CST-TONGLZ @return
     * List<T> @throws
     */
    protected List<T> find(CriteriaQuery<T> criteriaQuery) {
        TypedQuery<T> query = em.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
        return query.getResultList();
    }

    /**
     * @Title: find @Description:接受JPQL的条件查询,用于返回单个实体，如：结果集大于或者等于1条数据，则返回结果集的第一条数据。否则返回NULL @param
     * where条件 @author CST-TONGLZ @return List<T> @throws
     */
    public T find(String jpql, Map<String, Object> map) {
        TypedQuery<T> query = em.createQuery(jpql, entityClass).setFlushMode(FlushModeType.COMMIT);
        if (map != null) {
            for (String key : map.keySet()) {
                query.setParameter(key, map.get(key));
            }
        }
        query.setFirstResult(0).setMaxResults(1);// 设置查询1条
        List<T> list = query.getResultList();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    public T find(String jpql) {
        return find(jpql, null);
    }


    protected List<T> findAll(String jpql, Map<String, Object> map) {
        TypedQuery<T> query = em.createQuery(jpql, entityClass).setFlushMode(FlushModeType.COMMIT);
        if (map != null) {
            for (String key : map.keySet()) {
                query.setParameter(key, map.get(key));
            }
        }

        return query.getResultList();
    }

    /**
     * @Title: find4Jpql @Description:执行JPQL语句并装载指定类型 @param @author
     * CST-TONGLZ @return List<E> @throws
     */
    protected List<T> findAll(String jpql) {
        return findAll(jpql, null);
    }

    /**
     * @Title: update4Jpql @Description:执行批量删除JPQL @param @author
     * CST-TONGLZ @return @throws
     */
    protected int batchDelete(String jpql, Map<String, Object> map) {
        Query query = em.createQuery(jpql);
        for (String key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }
        return query.executeUpdate();
    }

    /**
     * @Title: batchUpdate @Description:批量更新JPQL @param @author CST-TONGLZ @return
     * int @throws
     */
    protected int batchUpdate(String jpql, Map<String, Object> map) {
        Query query = em.createQuery(jpql);
        for (String key : map.keySet()) {
            query.setParameter(key, map.get(key));
        }
        return query.executeUpdate();
    }

    /**
     * @param
     * @return List<Map < String, Object>>
     * @throws
     * @Title: findBySql
     * @Description:根据SQL查询
     * @author CST-TONGLZ
     */
    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> findBySql(String sql, Map<String, Object> paraMap) {
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
    protected List<Map<String, Object>> findBySql(String sql) {
        return findBySql(sql, null);
    }

    /**
     * @param
     * @return Object
     * @throws
     * @Title: findSingleResultBySql
     * @Description:获取单结果的SQL结果
     * @author CST-TONGLZ
     */
    protected Object findSingleResultBySql(String sql, Map<String, Object> paraMap) {
        Query query = em.createNativeQuery(sql);
        if (paraMap != null) {
            for (String key : paraMap.keySet()) {
                query.setParameter(key, paraMap.get(key));
            }
        }
        return query.getSingleResult();
    }

    protected int executeUpdateSql(String sql, Map<String, Object> params) {
        Query query = em.createNativeQuery(sql);
        if (params != null) {
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
        }
        return query.executeUpdate();
    }
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        return findPage(condition,em);
    }
    /**
     * @Title: findPage
     * @Description:获取分页数据，以Page数据格式返回
     * @param
     * @author CST-TONGLZ
     * @return Page<Map<String,Object>>
     * @throws
     */
    protected Page<Map<String, Object>> findPage(QueryCondition condition,EntityManager em) {
        if (condition == null)
            throw new RuntimeException(entityClass.getName() + "查询器不能为空");
        String sql = condition.getSQL();
        if (StringUtils.isEmpty(sql))
            throw new RuntimeException(entityClass.getName() + "SQL不能为空");

        long total = 0;
        int totalPages = 0;
        if (condition.getPageSize() > 0) {
            total = count4Condition(condition,em);
            totalPages = (int) (total + condition.getPageSize() - 1) / condition.getPageSize();
            if (totalPages < condition.getCurrentPage()) {
                condition.setCurrentPage(totalPages);
            }
        }
        List<Map<String, Object>> list = findListByCondition(condition,em);
        return new Page<Map<String, Object>>(list, total, condition);
    }
    @SuppressWarnings("unchecked")
    private int count4Condition(QueryCondition condition,EntityManager em) {
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
    protected List<Map<String, Object>> findListByCondition(QueryCondition condition,EntityManager em) {
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
    protected User getCurrentUser(){
        HttpSession session = getSession();
        LoginInfo loginInfo=(LoginInfo)session.getAttribute(Global.CUR_USER);
        if(loginInfo==null){
            throw new LoginException("","Session失效");
        }
        return loginInfo.getUser();
    }
    public HttpSession getSession() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        return request.getSession();
    }

}
