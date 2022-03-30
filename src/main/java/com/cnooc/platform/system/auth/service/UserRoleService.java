package com.cnooc.platform.system.auth.service;/**
 * @ClassName UserRoleService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月26日 19:04:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.auth.dao.UserRoleDao;
import com.cnooc.platform.system.auth.domain.UserRole;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: vels
 * @description: 用户角色服务
 * @author: TONG
 * @create: 2020-12-26 19:04
 **/
@Service
public class UserRoleService extends BaseService<UserRole> {
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserResourceService userResourceService;
    @Override
    public BaseDao<UserRole> getDao() {
        return userRoleDao;
    }
    public List<String> findRoleByUser(String userId){
        User user=new User();
        user.setId(userId);
        List<UserRole> urs=userRoleDao.findByUser(user);
        List<String> list=new ArrayList<>();
        for(UserRole ur:urs){
          list.add(ur.getRole().getId());
        }
        return list;
    }
    public List<String> findUserByRole(String roleId){
        Role role=new Role();
        role.setId(roleId);
        List<UserRole> urs=userRoleDao.findByRole(role);
        List<String> list=new ArrayList<>();
        for(UserRole ur:urs){
            list.add(ur.getUser().getId());
        }
        return list;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserRole( String userId, List<String> roleId){
        userRoleDao.deleteByUser(userId);
        flush();
        UserRole ur;
        Role r;
        User user=new User();
        user.setId(userId);
        for(String rid:roleId){
            ur=new UserRole();
            r=new Role();
            r.setId(rid);
            ur.setUser(user);
            ur.setRole(r);
            create(ur);
        }
        //重新生成用权限
        userResourceService.saveByUser(userId);
    }
}
