package com.cnooc.platform.system.auth.service;/**
 * @ClassName RoleResourceService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 19:07:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.auth.dao.RoleResourceDao;
import com.cnooc.platform.system.auth.domain.RoleResource;
import com.cnooc.platform.system.auth.domain.UserResource;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 角色资源服务
 * @author: TONG
 * @create: 2020-12-26 19:07
 **/
@Service
public class RoleResourceService extends BaseService<RoleResource> {
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private UserResourceService userResourceService;

    @Override
    public BaseDao<RoleResource> getDao() {
        return roleResourceDao;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getAuthByRole(String roleId){
        List<Map<String,Object>> auths= roleResourceDao.getAuthByRole(roleId);
        List<String> roleAuth=new ArrayList<>();
        for(Map<String,Object> row:auths){
            roleAuth.add(row.get("res").toString());
        }
        return roleAuth;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAuth( String roleId,  List<String> auths){
        roleResourceDao.deleteByRole(roleId);
        flush();
        Role role=new Role();
        role.setId(roleId);
        Resource res;
        RoleResource rr;
        for(String auth:auths){
            res =new Resource();
            rr=new RoleResource();
            res.setId(auth);
            rr.setRole(role);
            rr.setRes(res);
            create(rr);
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRoleByRes(List<String> roles,Resource res){
        roleResourceDao.deleteByRes(res);
        flush();
        for(String rid:roles){
            Role r=new Role();
            r.setId(rid);
            RoleResource rr=new RoleResource();
            rr.setRes(res);
            rr.setRole(r);
            create(rr);
            flush();
            //刷新权限
            userResourceService.saveUserRes(rid);
        }
    }

    public List<Role> getRoleByRes(String resId){
        List<RoleResource> rs= roleResourceDao.getRoleByRes(resId);
        List<Role> roles=new ArrayList<>();
        for(RoleResource r:rs){
            roles.add(r.getRole());
        }
        return roles;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByRes(String resId){
        Resource res=new Resource();
        res.setId(resId);
        roleResourceDao.deleteByRes(res);
    }

}
