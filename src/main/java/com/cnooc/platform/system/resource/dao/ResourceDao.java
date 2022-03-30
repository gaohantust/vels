package com.cnooc.platform.system.resource.dao;/**
 * @ClassName ResourceDao.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月01日 14:11:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.system.resource.domain.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName ResourceDao.java
 * @Description TODO
 * @createTime 2020年12月01日 14:11:00
 */
@Component
public class ResourceDao extends BaseDao<Resource> {
    public List<Map<String,Object>> getResourceByUser(String userId){
        String sql="select a.id,a.code,a.name,a.icon,a.levels,a.type,a.url,a.view_path,a.parent,a.dv from sys_resource a " +
                "right join (select distinct res from sys_user_res where sys_user=:user) b " +
                " on(a.id=b.res) where a.id is  not null order by a.sort_no";
        Map<String, Object> map=new HashMap<>();
        map.put("user",userId);
        List<Map<String,Object> >list=findBySql(sql,map);
        return list;
    }
    public List<Map<String,Object>> getMenuList(){
        String sql="select id,code,name,icon,type,url,view_path,parent,sort_no,part from sys_resource order by sort_no ";
        List<Map<String,Object> >list=findBySql(sql);
        return list;
    }
    public List<Map<String,Object>> getAuthList(){
        String sql="select id,code,name,icon,type,url,view_path,parent,sort_no,part from sys_resource where valid='Y'  order by sort_no ";
        List<Map<String,Object> >list=findBySql(sql);
        return list;
    }
}
