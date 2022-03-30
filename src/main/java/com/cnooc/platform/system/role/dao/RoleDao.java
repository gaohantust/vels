package com.cnooc.platform.system.role.dao;/**
 * @ClassName RoleDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月24日 16:18:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.role.domain.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 用户角色Dao
 * @author: TONG
 * @create: 2020-12-24 16:18
 **/
@Component
public class RoleDao extends BaseDao<Role> {
    @Override
    public Page<Map<String, Object>> findPage(QueryCondition condition) {
        String sql="select id,code,name,valid from sys_role ";
        condition.setSQL(sql);
        return super.findPage(condition);
    }
    public List<Map<String,Object>> findRoles(){
        String sql="select id,code,name from sys_role where valid='Y'";
        return findBySql(sql);
    }
}
