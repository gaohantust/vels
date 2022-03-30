package com.cnooc.platform.system.user.dao;

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.system.QueryConditionTransformTuple;
import org.hibernate.query.internal.NativeQueryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 9:38
 */
@Component
public class UserDao extends BaseDao<User> {
    @PersistenceContext(unitName = "maximoPersistenceUnit")
    @Autowired
    @Qualifier(value = "maximoEntityManager")
    protected EntityManager maximoEntityManager;
    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String sql="select id,code,name,valid,sex,email,phone,create_date,type from sys_user ";
        condition.setSQL(sql);
        return super.findPage(condition);
    }
    public Map<String,Object> getMaxUser(String code){
        String sql="select a.*,b.emailaddress from maxuser a left join email b on(a.personid=b.personid) where a.loginid =:code ";
        Query query = maximoEntityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(QueryConditionTransformTuple.INSTANCE);
        query.setParameter("code", code);
        List<Map<String,Object>>  list = query.getResultList();
        if(list.size()>0)
            return list.get(0);
        return null;
    }
    public List<Map<String, Object>> getParts(String userId){
        String sql="select b.id,b.code,b.name,b.icon,b.url,b.view_path,b.dv from sys_user_res a left join sys_resource b on(a.res=b.id ) " +
                " where a.sys_user=:userId and b.type='1' and valid='Y' and part='Y' order by sort_no";
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        return findBySql(sql,map);
    }
}
