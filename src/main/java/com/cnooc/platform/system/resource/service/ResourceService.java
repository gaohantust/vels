package com.cnooc.platform.system.resource.service;/**
 * @ClassName Resource.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月01日 14:13:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.resource.dao.ResourceDao;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName Resource.java
 * @Description TODO
 * @createTime 2020年12月01日 14:13:00
 */
@Service
public class ResourceService extends BaseService<Resource> {
    @Autowired
    private ResourceDao dao;

    @Override
    public BaseDao<Resource> getDao() {
        return dao;
    }

    public List<Map<String,Object>> getMenuList(){
        List<Map<String,Object>> list=dao.getMenuList();
        List<Map<String, Object>> returnList = new ArrayList<>();
        toMenuTreeList(list,returnList);
        return returnList;
    }
    public List<Map<String,Object>> getAuthList(){
        List<Map<String,Object>> list=dao.getAuthList();
        List<Map<String, Object>> returnList = new ArrayList<>();
        toMenuTreeList(list,returnList);
        return returnList;
    }
    /**
     *菜单管理-菜单树列表
     * @param res
     * @param list
     * @author TONG
     * @date 2020/12/12 19:35 
     * @return void
     */
    private void toMenuTreeList(List<Map<String, Object>> res, List<Map<String, Object>> list) {
        for (Map<String, Object> row : res) {
            String parent = getString(row, "parent");
            if(!row.containsKey("next")){
                row.put("next", new ArrayList<>());
            }
            if (parent == null || parent.length() == 0) {
                addMenuNext(res, row);
                list.add(row);
            }
        }
    }
    /**
     *递归添加下级菜单
     * @param res
     * @param parentRow
     * @author TONG
     * @date 2020/12/12 21:00 
     * @return void
     */
    private void addMenuNext(List<Map<String, Object>> res, Map<String, Object> parentRow) {
        String key = "parent";
        String nextKey = "next";
        String parentId = getString(parentRow, "id");
        List<Map<String, Object>> next;
        for (Map<String, Object> row : res) {
            String parent = getString(row, key);
            if (parent.equals(parentId)) {
                next = new ArrayList<>();
                if (parentRow.containsKey(nextKey)) {
                    next = (List<Map<String, Object>>) parentRow.get(nextKey);
                }
                addMenuNext(res,row);
                next.add(row);
                parentRow.put(nextKey, next);
            }
        }
    }
    /**
     * 获取当前用户权限信息 
     * @author TONG
     * @date 2020/12/12 19:36
     * @return java.util.Map<java.lang.String, java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
     */
    public void setPermsion(LoginInfo loginInfo) {
        String userId=loginInfo.getUser().getId();
        List<Map<String, Object>> res = dao.getResourceByUser(userId);
        List<Map<String, Object>> list = new ArrayList<>();
        toTreeList(res, list);
        loginInfo.setResource(list);
        loginInfo.setRoutes(getRoutes(res));
        loginInfo.setAuth(getAuth(res));
    }
    private List<String> getAuth(List<Map<String, Object>> res){
        List<String> list=new ArrayList<>();
        for(Map<String, Object> row : res){
            list.add(getString(row,"code"));
        }
        return list;
    }
    /**
     *获取路由信息
     * @param res
     * @author TONG
     * @date 2020/12/12 21:01 
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     */
    private List<Map<String, Object>> getRoutes(List<Map<String, Object>> res) {
        List<Map<String, Object>> routes = new ArrayList<>();
        for (Map<String, Object> row : res) {
            String type = getString(row, "type");
            String dv = getString(row, "dv");
            if (!"1".equals(type)) {
                continue;
            }
            Map<String, Object> route = new HashMap<>();
            route.put("name", getString(row, "name"));
            route.put("url", getString(row, "url"));
            route.put("view_path", getString(row, "view_path"));
            route.put("dv", getString(row, "dv"));
            routes.add(route);
        }
        return routes;
    }
    /**
     *构建系统导航菜单树结构
     * @param res
     * @param list
     * @author TONG
     * @date 2020/12/12 21:01 
     * @return void
     */
    private void toTreeList(List<Map<String, Object>> res, List<Map<String, Object>> list) {
        String key = "parent";
        for (Map<String, Object> row : res) {
            String parent = getString(row, key);
            List<Map<String, Object>> next = new ArrayList<>();
            row.put("next", next);
            if (parent == null || parent.length() == 0) {
                list.add(row);
                addNext(res, row);
            }
        }
    }
    /**
     *构建系统导航添加下级节点
     * @param res
     * @param parentRow
     * @author TONG
     * @date 2020/12/12 21:02 
     * @return void
     */
    private void addNext(List<Map<String, Object>> res, Map<String, Object> parentRow) {
        String key = "parent";
        String nextKey = "next";
        String parentId = getString(parentRow, "id");
        for (Map<String, Object> row : res) {
            String type=getString(row,"type");
            if("2".equals(type)||"0".equals(type)){
                continue;
            }
            String parent = getString(row, key);
            if (parent.equals(parentId)) {
                List<Map<String, Object>> next = new ArrayList<>();
                if (parentRow.containsKey(nextKey)) {
                    next = (List<Map<String, Object>>) parentRow.get(nextKey);
                }
                next.add(row);
                parentRow.put(nextKey, next);
                addNext(res,row);
            }
        }
    }
}
