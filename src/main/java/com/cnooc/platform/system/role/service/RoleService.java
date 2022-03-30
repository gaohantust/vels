package com.cnooc.platform.system.role.service;/**
 * @ClassName RoleService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月24日 16:20:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.auth.service.RoleResourceService;
import com.cnooc.platform.system.auth.service.UserResourceService;
import com.cnooc.platform.system.auth.service.UserRoleService;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.resource.service.ResourceService;
import com.cnooc.platform.system.role.dao.RoleDao;
import com.cnooc.platform.system.role.domain.Role;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 用户角色服务
 * @author: TONG
 * @create: 2020-12-24 16:20
 **/
@Service
public class RoleService extends BaseService<Role> {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private UserResourceService userResourceService;
    @Override
    public BaseDao<Role> getDao() {
        return roleDao;
    }
    public List<Map<String,Object>> findRoles(){
        return roleDao.findRoles();
    }
    public Map<String,Object>findUserRole(String userId){
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("roles",findRoles());
        List<String> rs=userRoleService.findRoleByUser(userId);
        returnMap.put("user_roles",rs);
        return returnMap;
    }
    public Map<String,Object> getAuth(String roleId){
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String,Object>> auths=resourceService.getAuthList();
        List<String> roleAuth=roleResourceService.getAuthByRole(roleId);
        returnMap.put("auths",auths);
        returnMap.put("roleAuth",roleAuth);
        return  returnMap;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAuth( String roleId,  List<String> auths){
        roleResourceService.saveAuth(roleId,auths);
        flush();
        //重新设定权限
        userResourceService.saveUserRes(roleId);
    }
}
