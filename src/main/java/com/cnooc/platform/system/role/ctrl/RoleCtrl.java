package com.cnooc.platform.system.role.ctrl;/**
 * @ClassName RoleCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月24日 16:21:00
 */

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.role.service.RoleService;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 用户角色Ctrl
 * @author: TONG
 * @create: 2020-12-24 16:21
 **/
@RestController
@RequestMapping("/role")
public class RoleCtrl  extends BaseCtrl<Role> {
    @Autowired
    private RoleService roleService;

    @Override
    public BaseService<Role> getService() {
        return roleService;
    }
    @RequestMapping("findUserRole.do")
    public String findUserRole(@RequestBody String userId){
        return JSONUtil.getJson(roleService.findUserRole(userId));
    }
    @RequestMapping("getAuth.do")
    public String getAuth(@RequestBody String roleId){
        return JSONUtil.getJson(roleService.getAuth(roleId));
    }
    @RequestMapping("saveAuth/{role}.do")
    public String saveAuth(@PathVariable("role") String roleId, @RequestBody List<String> auths){
        roleService.saveAuth(roleId,auths);
        return "{}";
    }
}
