package com.cnooc.platform.system.dic.dao;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.page.Filter;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.dic.domain.Dic;
import com.cnooc.platform.util.StringUtils;
import com.cnooc.platform.util.system.QueryConditionTransformTuple;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName DicDao.java
 * @Description TODO
 * @createTime 2020年12月14日 20:22:00
 */
@Component
public class DicDao extends BaseDao<Dic> {
    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String sql="select id,code,name,valid from sys_dic where parent is null";
        condition.setSQL(sql);
        return super.findPage(condition);
    }

    public List<Map<String,Object>> findByTable(String table, String query, List<Filter> filters) {
        StringBuffer buffer = new StringBuffer("select id,code,name from ");
        buffer.append(table);
        buffer.append(" where valid='Y' ");
        Map<String, Object> map = new HashMap<String, Object>();
        for (Filter f : filters) {
            buffer.append(f.getExpression(map));
        }
        if (StringUtils.isNotEmpty(query)) {
            query = query.toLowerCase();
            buffer.append(" and ");
            buffer.append(" lower((code||name)) like :codeName");
            map.put("codeName", "%"+query+"%");
        }
        String orderBy=" order by id ";
            buffer.append(" ").append(orderBy).append(" ");
        Query emQuery = em.createNativeQuery(buffer.toString());
        for (String key : map.keySet())
            emQuery.setParameter(key, map.get(key));
        emQuery.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        emQuery.setFirstResult(0).setMaxResults(30);
        return emQuery.getResultList();
    }
    public List<Map<String,Object>> findByParent( String id) {
        String sql="select id,code,name,valid from sys_dic where parent=:parent";
        Map<String ,Object> map = new HashMap<>();
        map.put("parent",id);
        return findBySql(sql,map);
    }

}
