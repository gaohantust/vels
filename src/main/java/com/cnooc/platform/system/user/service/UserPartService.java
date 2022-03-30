package com.cnooc.platform.system.user.service;/**
 * @ClassName UserPartService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月14日 09:17:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.user.dao.UserPartDao;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.system.user.domain.UserPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: vels
 * @description: 用户部件服务
 * @author: TONG
 * @create: 2021-01-14 09:17
 **/
@Service
public class UserPartService extends BaseService<UserPart> {
    @Autowired
    private UserPartDao dao;

    @Override
    public BaseDao<UserPart> getDao() {
        return dao;
    }
    public List<UserPart> getByUser(User user){
        return dao.getByUser(user);
    }
    public void delByUser(User user){
        dao.delByUser(user.getId());
    }
}
