package com.cnooc.maximo.security.service;
/**
 * @ClassName SecurityService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月18日 10:07:00
 */

import com.cnooc.maximo.dao.MaximoDao;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @program: vels
 * @description: 安全统计服务
 * @author: TONG
 * @create: 2021-03-18 10:07
 **/
@Service
@Transactional
public class SecurityService {
    @Autowired
    private MaximoDao maximoDao;
    public Map<String,Object> equiCategoryRep(QueryCondition condition){
        condition.setPageSize(-1);
        String sql="select DECODE(H.HDLEIBIE, '管理缺陷类','1', '管理方面','1', 'Management','1', '安全环保类','2', '环境类','2', 'Environment','2', '设备设施类','3', '设备设施方面','3', 'Equipment & Facility','3', '井筒类','4', 'Wellhead', '4', '工艺流程类','5', '钻井类','6', '人的不安全行为类','0', 'Improper Personal Behavior','0', '其它','0', 'Others','0', null,'0', '0') wopm3, DECODE(H.HDLEIBIE, '管理缺陷类','管理缺陷类', '管理方面','管理缺陷类', 'Management','管理缺陷类', 'Environment','安全环保类' ,'安全环保类','安全环保类', '环境类','安全环保类', '设备设施类','设备设施类', '设备设施方面','设备设施类', 'Equipment & Facility','设备设施类', '井筒类','井筒类', 'Wellhead', '井筒类', '工艺流程类','工艺流程类', '钻井类','钻井类', '其它','其它', '人的不安全行为类','其它', 'Improper Personal Behavior','其它', 'Others','其它', null,'其它','其它') L_DESC ,count(1) hrs from EXT_MV_I303 H WHERE block is not null group by DECODE(H.HDLEIBIE, '管理缺陷类','1', '管理方面','1', 'Management','1', '安全环保类','2', '环境类','2', 'Environment','2', '设备设施类','3', '设备设施方面','3', 'Equipment & Facility','3', '井筒类','4', 'Wellhead', '4', '工艺流程类','5', '钻井类','6', '人的不安全行为类','0', 'Improper Personal Behavior','0', '其它','0', 'Others','0', null,'0', '0'), DECODE(H.HDLEIBIE, '管理缺陷类','管理缺陷类', '管理方面','管理缺陷类', 'Management','管理缺陷类', 'Environment','安全环保类' ,'安全环保类','安全环保类', '环境类','安全环保类', '设备设施类','设备设施类', '设备设施方面','设备设施类', 'Equipment & Facility','设备设施类', '井筒类','井筒类', 'Wellhead', '井筒类', '工艺流程类','工艺流程类', '钻井类','钻井类', '其它','其它', '人的不安全行为类','其它', 'Improper Personal Behavior','其它', 'Others','其它', null,'其它','其它') order by wopm3";
        condition.setSQL(sql);
        Page<Map<String,Object>> page=maximoDao.findPage(condition);
        Map<String,Object> map=new HashMap<>();
        List<Map<String,Object>> data=page.getData();
        map.put("rows",data);
        map.put("option",getPieOption("设备类别","",data,new String[]{"l_desc","hrs"}));
        return map;
    }
    private Map<String,Object> getPieOption(String title,String subtext,List<Map<String,Object>> data,String[] keys) {
        Map<String, Object> option = new HashMap<>();
        Map<String, Object> titleMap = new HashMap<>();
        titleMap.put("text", title);
        titleMap.put("subtext", subtext);
        titleMap.put("left", "center");
        Map<String, Object> tooltip = new HashMap<>();
        tooltip.put("trigger", "item");
        tooltip.put("formatter", "{b} <br/>数量 : {c} <br/> 占比 : <font style='color:red'>{d}%</font>");
        Map<String, Object> legend = new HashMap<>();
        legend.put("orient", "vertical");
        legend.put("left", "left");
        List<Map<String, Object>> series = new ArrayList<>();
        Map<String, Object> seriesMap = new HashMap<>();
        seriesMap.put("name", title);
        seriesMap.put("type", "pie");
        seriesMap.put("radius", "50%");
        List<Map<String, Object>> seriesData = new ArrayList<>();
        String nameKey=keys[0];
        String valueKey=keys[1];
        for(Map<String,Object> row:data){
            Map<String,Object> tempRow=new HashMap<>();
            tempRow.put("name",row.get(nameKey));
            tempRow.put("value",row.get(valueKey));
            seriesData.add(tempRow);
        }
        seriesMap.put("data", seriesData);
        Map<String, Object> seriesEmphasis = new HashMap<>();
        Map<String, Object> seriesEmphasisItemStyle = new HashMap<>();
        seriesEmphasisItemStyle.put("shadowBlur",10);
        seriesEmphasisItemStyle.put("shadowOffsetX",1);
        seriesEmphasisItemStyle.put("shadowColor","rgba(0, 0, 0, 0.5)");
        seriesEmphasis.put("itemStyle",seriesEmphasisItemStyle);
        seriesMap.put("emphasis", seriesEmphasis);
        series.add(seriesMap);
        option.put("title",titleMap);
        option.put("tooltip",tooltip);
        option.put("legend",legend);
        option.put("series",series);
       return option;
    }
}
