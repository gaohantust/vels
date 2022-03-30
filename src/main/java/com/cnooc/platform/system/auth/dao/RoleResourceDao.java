package com.cnooc.platform.system.auth.dao;/**
 * @ClassName RoleResourceDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 19:02:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.auth.domain.RoleResource;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.role.domain.Role;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 角色资源Dao
 * @author: TONG
 * @create: 2020-12-26 19:02
 **/
@Component
public class RoleResourceDao extends BaseDao<RoleResource> {
    public List<Map<String,Object>> getAuthByRole(String roleId){
        String sql="select res from sys_role_res where role=:role";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("role",roleId);
        return findBySql(sql,map);
    }
    public void deleteByRole(String role){
        String sql="delete from sys_role_res where role=:role";
        Map<String,Object> map=new HashMap<>();
        map.put("role",role);
        super.executeUpdateSql(sql,map);
    }
    public void deleteByRes(Resource res){
        String sql="delete from sys_role_res where res=:res";
        Map<String,Object> map=new HashMap<>();
        map.put("res",res);
        super.executeUpdateSql(sql,map);
    }
    public List<RoleResource> getRoleByRes(String resId){
        String jpql="from RoleResource where res.id=:res";
        Map<String,Object> param=new HashMap<>();
        param.put("res",resId);
        return findAll(jpql,param);
    }
}
