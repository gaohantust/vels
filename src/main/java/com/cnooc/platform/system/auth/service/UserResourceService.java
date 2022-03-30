package com.cnooc.platform.system.auth.service;/**
 * @ClassName UserResourceService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 19:06:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.auth.dao.UserResourceDao;
import com.cnooc.platform.system.auth.domain.RoleResource;
import com.cnooc.platform.system.auth.domain.UserResource;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: vels
 * @description: 用户资源服务
 * @author: TONG
 * @create: 2020-12-26 19:06
 **/
@Service
public class UserResourceService extends BaseService<UserResource> {
    @Autowired
    private UserResourceDao userResourceDao;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Override
    public BaseDao<UserResource> getDao() {
        return userResourceDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserRes(String roleId) {
        userResourceDao.deleteByRole(roleId);
        flush();
        List<String> users = userRoleService.findUserByRole(roleId);
        List<String> res = roleResourceService.getAuthByRole(roleId);
        UserResource ur;
        Resource resource;
        User user;
        for (String uid : users) {
            for (String r : res) {
                ur = new UserResource();
                resource = new Resource();
                user = new User();
                user.setId(uid);
                resource.setId(r);
                ur.setFrom_role_id(roleId);
                ur.setFrom_type("role");
                ur.setRes(resource);
                ur.setUser(user);
                create(ur);
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveByUser(String userId) {
        userResourceDao.deleteByUser(userId);
        flush();
        List<String> roles = userRoleService.findRoleByUser(userId);
        User user=new User();
        user.setId(userId);
        UserResource ur;
        Resource resource;
        for(String r:roles){
            List<String> resources=roleResourceService.getAuthByRole(r);
            for(String res:resources){
                ur = new UserResource();
                resource = new Resource();
                resource.setId(res);
                ur.setFrom_role_id(r);
                ur.setFrom_type("role");
                ur.setRes(resource);
                ur.setUser(user);
                create(ur);
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserAuth(List<String> users,Resource res){
        userResourceDao.deleteByResAuth(res.getId());
        flush();
        for(String userid:users){
            User user=new User();
            user.setId(userid);
            UserResource ur=new UserResource();
            ur.setFrom_type("user");
            ur.setUser(user);
            ur.setRes(res);
            save(ur);
        }
    }
    public List<User> getUserByRes(String resId){
        List<UserResource> urs= userResourceDao.getUserByRes(resId);
        List<User> users=new ArrayList<>();
        for(UserResource ur:urs){
            users.add(ur.getUser());
        }
        return users;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByRes(String resId){
        userResourceDao.deleteByRes(resId);
    }
}
