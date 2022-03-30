package com.cnooc.platform.system.auth.dao;/**
 * @ClassName UserRole.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 18:59:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.auth.domain.UserRole;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 用户角色Dao
 * @author: TONG
 * @create: 2020-12-26 18:59
 **/
@Component
public class UserRoleDao extends BaseDao<UserRole> {
    public List<UserRole> findByUser(User user) {
        String jqpl="from UserRole where user=:user";
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        return findAll(jqpl,map);
    }
    public List<UserRole> findByRole(Role role) {
        String jqpl="from UserRole where role=:role";
        Map<String,Object> map=new HashMap<>();
        map.put("role",role);
        return findAll(jqpl,map);
    }
    public void deleteByUser(String user){
        String sql="DELETE FROM SYS_USER_ROLE WHERE sys_user=:user";
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        super.executeUpdateSql(sql,map);
    }
}
