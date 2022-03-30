package com.cnooc.platform.system.user.dao;/**
 * @ClassName UserPartDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月14日 09:16:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.system.user.domain.UserPart;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 用户组件Dao
 * @author: TONG
 * @create: 2021-01-14 09:16
 **/
@Component
public class UserPartDao extends BaseDao<UserPart> {
    public List<UserPart> getByUser(User user){
        String jpql="from UserPart where user=:user and res.part=:part";
        Map<String,Object> map=new HashMap<>();
        map.put("user",user);
        map.put("part",true);
        return findAll(jpql,map);
    }
    public void delByUser(String user) {
        String sql="delete from sys_user_part where sys_user=:user";
        Map<String, Object> map=new HashMap<>();
        map.put("user",user);
        super.executeUpdateSql(sql,map);
    }

}
