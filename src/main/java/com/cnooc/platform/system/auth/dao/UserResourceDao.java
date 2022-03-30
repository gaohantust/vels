package com.cnooc.platform.system.auth.dao;/**
 * @ClassName UserResourceDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 19:01:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.auth.domain.UserResource;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 角色资源Dao
 * @author: TONG
 * @create: 2020-12-26 19:01
 **/
@Component
public class UserResourceDao extends BaseDao<UserResource> {
    public void deleteByRole(String role){
        String sql="delete from sys_user_res where from_type='role' and from_role_id=:role";
        Map<String,Object> map=new HashMap<>();
        map.put("role",role);
        super.executeUpdateSql(sql,map);
    }
    public void deleteByUser(String user){
        String sql="delete from sys_user_res where from_type='role' and  sys_user=:user";
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        super.executeUpdateSql(sql,map);
    }
    public void deleteByResAuth(String resId){
        String sql="delete from sys_user_res where from_type='user' and  res=:res";
        Map<String,Object> map=new HashMap<>();
        map.put("res",resId);
        super.executeUpdateSql(sql,map);
    }
    public List<UserResource> getUserByRes(String resId){
        String jpql="from UserResource where from_type='user' and res.id=:res";
        Map<String,Object> param=new HashMap<>();
        param.put("res",resId);
        return findAll(jpql,param);
    }
    public void deleteByRes(String resId){
        String sql="delete from sys_user_res where res=:res";
        Map<String,Object> map=new HashMap<>();
        map.put("res",resId);
        super.executeUpdateSql(sql,map);
    }
}
