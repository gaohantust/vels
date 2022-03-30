package com.cnooc.platform.datav.config.service;/**
 * @ClassName DVConfService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 09:51:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.bean.DVConfLineBean;
import com.cnooc.platform.datav.config.bean.DVConfPieBean;
import com.cnooc.platform.datav.config.bean.DVConfSuper;
import com.cnooc.platform.datav.config.bean.DVConfTableBean;
import com.cnooc.platform.datav.config.dao.DVConfDao;
import com.cnooc.platform.datav.config.domain.*;
import com.cnooc.platform.datav.dataset.service.DataSetService;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.system.auth.domain.UserResource;
import com.cnooc.platform.system.auth.service.RoleResourceService;
import com.cnooc.platform.system.auth.service.UserResourceService;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.resource.service.ResourceService;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: DVConf 服务
 * @author: TONG
 * @create: 2021-03-31 09:51
 **/
@Service
public class DVConfService extends BaseService<DVConf> {
    @Autowired
    private DVConfDao dao;
    @Autowired
    private DVConfTableService dvConfTableService;
    @Autowired
    private DVConfPieService dvConfPieService;
    @Autowired
    private DVConfLineService dvConfLineService;
    @Autowired
    private DVConfQueryService dvConfQueryService;
    @Autowired
    private DataSetService dataSetService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserResourceService userResourceService;
    @Autowired
    private RoleResourceService roleResourceService;

    @Override
    public BaseDao<DVConf> getDao() {
        return dao;
    }

    @Override
    public void setDef(DVConf entity) {
        if (entity.isNew()) {
            entity.setSys_user(getCurrentUser());
        }
        super.setDef(entity);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public DVConfSuper getByCode(String code){
        DVConf conf=findByCode(code);
        return getOtherConf(conf);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public DVConfSuper getConf(String id){
        DVConf conf=findByID(id);
        return getOtherConf(conf);
    }
    private DVConfSuper getOtherConf(DVConf conf){
        List<DVConfQuery> queries=dvConfQueryService.getByConf(conf);
        String type=conf.getType();
        if("table".equals(type)){
            DVConfTableBean data=new DVConfTableBean();
            data.setConf(conf);
            data.setQuerys(queries);
            data.setTables(dvConfTableService.getByConf(conf));
            return data;
        }else if("pie".equals(type)){
            DVConfPieBean data=new DVConfPieBean();
            data.setConf(conf);
            data.setQuerys(queries);
            data.setPie(dvConfPieService.getByConf(conf));
            return data;
        }else if("line_bar".equals(type)){
            DVConfLineBean data=new DVConfLineBean();
            data.setConf(conf);
            data.setQuerys(queries);
            data.setLines(dvConfLineService.getByConf(conf));
            return data;
        }else{
            DVConfTableBean data=new DVConfTableBean();
            data.setConf(conf);
            data.setQuerys(queries);
            return data;
        }
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public DVConfSuper saveTable(DVConfTableBean data) {
        //保存前处理
        DVConf conf=prefixSave(data);
        //删除数据表格配置信息
        dvConfTableService.delByConf(conf);
        //保存数据表格配置信息
        List<DVConfTable> tables=data.getTables();
        for(int i=0;i<tables.size();i++){
            DVConfTable table=tables.get(i);
            table.setSort_no(i);
            table.setConf(conf);
            dvConfTableService.save(table);
        }
        data.setConf(conf);
        return data;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public DVConfSuper savePie(DVConfPieBean data) {
        //保存前处理
        DVConf conf=prefixSave(data);
        //删除饼图配置信息
        dvConfPieService.delByConf(conf);
        //保存饼图配置信息
        DVConfPie pie=data.getPie();
        pie.setConf(conf);
        dvConfPieService.save(pie);
        data.setConf(conf);
        return data;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public DVConfSuper saveLine(DVConfLineBean data) {
        //保存前处理
        DVConf conf=prefixSave(data);
        //删除折线配置信息
        dvConfLineService.delByConf(conf);
        //保存折线配置信息
        List<DVConfLine> lines=data.getLines();
        for(DVConfLine line:lines){
            line.setConf(conf);
            dvConfLineService.save(line);
        }
        data.setConf(conf);
        return data;
    }
    private DVConf prefixSave(DVConfSuper data){
        DVConf conf=data.getConf();
        conf.setSys_user(getCurrentUser());
        //保存主表
        save(conf);
        conf=findByID(conf.getId());
        //删除所有查询条件
        dvConfQueryService.delByConf(conf);
        //保存查询条件
        List<DVConfQuery> queries=data.getQuerys();
        for(DVConfQuery q:queries){
            q.setConf(conf);
            dvConfQueryService.save(q);
        }
        flush();
        return conf;
    }
    /**
     *按照可视化配置获取页面显示数据
     * @param id
     * @param condition
     * @author TONG
     * @date 2021/4/14 9:36
     * @return java.lang.Object
     */
    public Object getConfData(String id, QueryCondition condition){
        DVConf conf=findByID(id);
        String dataSetId=conf.getDataSet().getId();
        String showType=conf.getType();
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("conf",conf);
        if("table".equals(showType)){
            Page page=conf.isPage()?dataSetService.getPageData(dataSetId,condition):dataSetService.getData(dataSetId,condition);
            returnMap.put("data",page);
            return returnMap;
        }
        if("pie".equals(showType)){
            DVConfPieBean bean=new DVConfPieBean();
            DVConfPie pie=dvConfPieService.getByConf(conf);
            bean.setConf(conf);
            bean.setPie(pie);
            Map<String,Object> data = dataSetService.getPieData(bean,condition);
            returnMap.put("data",data);
            return returnMap;
        }
        if("line_bar".equals(showType)){
            DVConfLineBean bean=new DVConfLineBean();
            List<DVConfLine> list=dvConfLineService.getByConf(conf);
            bean.setConf(conf);
            bean.setLines(list);
            Map<String,Object> data =dataSetService.getLineData(bean,condition);
            returnMap.put("data",data);
            return returnMap;
        }
        return null;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void createMenu(String id){
        String parentCode="report";
        Resource parent=resourceService.findByCode(parentCode);
        DVConf conf=findByID(id);
        conf.setStatus(1);
        Resource resource=new Resource();
        resource.setDv(true);
        resource.setUrl("/_dv_"+conf.getCode());
        resource.setPart(true);
        resource.setType("1");
        resource.setView_path("/datav/dv/index");
        resource.setSort_no(parent.getSort_no()+5);
        resource.setCode("dv_"+conf.getCode());
        resource.setName(conf.getName());
        resource.setLeafe(false);
        resource.setParent(parent);
        String icon="";
        if("table".equals(conf.getType())){
            icon="el-icon-ali-calculator";
        }else if("pie".equals(conf.getType())){
            icon="el-icon-ali-chart-pie";
        }else{
            icon="el-icon-ali-data-view";
        }
        resource.setIcon(icon);
        resourceService.save(resource);
        conf.setRes_id(resource.getId());
        flush();
        //设置权限
        UserResource ur=new UserResource();
        ur.setRes(resource);
        ur.setFrom_type("user");
        ur.setUser(getCurrentUser());
        userResourceService.save(ur);
    }
    public Map<String,Object> getShareInfo(String id){
        DVConf conf=findByID(id);
        String resId=conf.getRes_id();
        List<User> users=userResourceService.getUserByRes(resId);
        List<Role> roles=roleResourceService.getRoleByRes(resId);
        Map<String,Object> returnMap=new HashMap<>();
        returnMap.put("users",users);
        returnMap.put("roles",roles);
        returnMap.put("id",id);
        return returnMap;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void shareSave(String id,List<String> users,List<String> roles){
        DVConf conf=findByID(id);
        conf.setStatus(2);
        String resId=conf.getRes_id();
        Resource res=new Resource();
        res.setId(resId);
        //保存用户权限
        userResourceService.saveUserAuth(users,res);
        //保存角色权限
        roleResourceService.saveRoleByRes(roles,res);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String id) {
        DVConf conf=findByID(id);
        //获取菜单id
        String resId = conf.getRes_id();
        //删除菜单相关权限信息
        userResourceService.deleteByRes(resId);
        //删除角色菜单相关信息
        roleResourceService.deleteByRes(resId);
        //删除菜单信息
        resourceService.delete(resId);
        //删除配置信息
        super.delete(id);
    }
}
