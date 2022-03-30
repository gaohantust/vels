package com.cnooc.platform.datav.dataset.service;
/**
 * @ClassName DataSetService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月26日 11:43:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.bean.DVConfLineBean;
import com.cnooc.platform.datav.config.bean.DVConfPieBean;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.domain.DVConfLine;
import com.cnooc.platform.datav.config.domain.DVConfPie;
import com.cnooc.platform.datav.config.service.DVConfPieService;
import com.cnooc.platform.datav.dataset.dao.DataSetDao;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.datav.dataset.domain.DataSetCol;
import com.cnooc.platform.exception.bean.ServiceException;
import com.cnooc.platform.page.Page;
import com.cnooc.platform.page.QueryCondition;
import com.sun.javafx.charts.Legend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

/**
 * @program: vels
 * @description: 数据集服务
 * @author: TONG
 * @create: 2021-03-26 11:43
 **/
@Service
public class DataSetService extends BaseService<DataSet> {
    @Autowired
    private DataSetDao dao;
    @Autowired
    private DataSetColService dataSetColService;
    @Override
    public BaseDao<DataSet> getDao() {
        return dao;
    }
    public Map<String,Object> load( String id){
        DataSet temp = findByID(id);
        List<DataSetCol> cs = dataSetColService.getByDataSet(temp);
        Map<String,Object> map=new HashMap<>();
        map.put("cs",cs);
        map.put("ds",temp);
        return map;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Object step(int step,DataSet ds){
        if(step==0){//保存、验证
            try {
                dao.checkSQL(ds);
            } catch (Exception e) {
                throw new ServiceException("sql","您填写的SQL信息执行错误，请重新填写");
            }
            ds.setSteps(1);
            ds.setValid(false);
            ds.setSys_user(getCurrentUser());
            save(ds);
            return findByID(ds.getId());
        }
        if(step==1){//获取元数据
            DataSet temp = findByID(ds.getId());
            temp.setSteps(2);
            dataSetColService.delByDataSet(temp);
            //存库元数据
            List<DataSetCol> cs = dao.getDataSetMeta(temp);
            for(DataSetCol c : cs){
                c.setDataSet(temp);
                dataSetColService.create(c);
            }
            flush();
            cs = dataSetColService.getByDataSet(temp);
            Map<String,Object> map=new HashMap<>();
            map.put("cs",cs);
            map.put("ds",temp);
            return map;
        }
        throw new ServiceException("step","没有对应的步骤信息");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveCol(String dsId, List<DataSetCol> cs){
        DataSet dataSet=findByID(dsId);
        dataSet.setSteps(3);
        dataSet.setValid(true);
        for(DataSetCol c:cs){
            dataSetColService.save(c);
        }
    }
    public List<DataSetCol> getCols(String id){
        DataSet dataSet=new DataSet();
        dataSet.setId(id);
        return dataSetColService.getByDataSet(dataSet);
    }
    /**
     *根据数据集获取分页数据
     * @param dataSetId
     * @param condition
     * @author TONG
     * @date 2021/4/14 9:35
     * @return com.cnooc.platform.page.Page
     */
    public Page getPageData(String dataSetId,QueryCondition condition){
        DataSet dataSet=findByID(dataSetId);
        return dao.getPageData(dataSet,condition);
    }
    /**
     *根据数据集获取数据
     * @param dataSetId
     * @param condition
     * @author TONG
     * @date 2021/4/14 9:35
     * @return com.cnooc.platform.page.Page
     */
    public Page getData(String dataSetId,QueryCondition condition){
        DataSet dataSet=findByID(dataSetId);
        return dao.getData(dataSet,condition);
    }
    /**
     *获取饼图数据
     * @param bean
     * @param condition
     * @author TONG
     * @date 2021/4/15 14:57
     * @return com.cnooc.platform.page.Page
     */
    public Map<String, Object> getPieData(DVConfPieBean bean, QueryCondition condition){
        DVConf conf=bean.getConf();
        DataSet dataSet=findByID(conf.getDataSet().getId());
        Page page = dao.getData(dataSet,condition);
        List<Map<String,Object>> list=page.getData();
        String title = conf.getName();
        DVConfPie pie= bean.getPie();
        String nameKey=pie.getCol_label();
        String valueKey=pie.getCol_value();
        Map<String, Object> option = new HashMap<>();
        Map<String, Object> titleMap = new HashMap<>();
        titleMap.put("text", title);
        titleMap.put("subtext", "");
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
        for(Map<String,Object> row:list){
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
        //toolbox
        Map<String, Object> toolbox = new HashMap<>();
        Map<String, Object> feature = new HashMap<>();
        Map<String, Object> dataView =getMap(new KV("show",true),new KV("readOnly",true));
        Map<String, Object> saveAsImage =getMap(new KV("show",true));
        feature.put("dataView",dataView);
        feature.put("saveAsImage",saveAsImage);
        toolbox.put("feature",feature);
        option.put("toolbox",toolbox);
        //toolbox
        return option;
    }
    public Map<String, Object> getLineData(DVConfLineBean bean, QueryCondition condition){
        DVConf conf=bean.getConf();
        DataSet dataSet=findByID(conf.getDataSet().getId());
        Page page = dao.getData(dataSet,condition);
        List<Map<String,Object>> data=page.getData();
        List<DVConfLine> lines=bean.getLines();
        boolean isHasYser=isHasYser(lines);
        Map<String, Object> option = new HashMap<>();
        //tooltip
        Map<String, Object> tooltip = getMap(new KV("trigger","axis"));
        Map<String, Object> axisPointer = getMap(new KV("type","cross"));
        Map<String, Object> crossStyle = getMap(new KV("color","#999"));
        axisPointer.put("crossStyle",crossStyle);
        tooltip.put("axisPointer",axisPointer);
        option.put("tooltip",tooltip);
        //toolbox
        Map<String, Object> toolbox = new HashMap<>();
        Map<String, Object> feature = new HashMap<>();
        Map<String, Object> dataView =getMap(new KV("show",true),new KV("readOnly",true));
        List<String> magicTypes=new ArrayList<>();
        magicTypes.add("line");
        magicTypes.add("bar");
        Map<String, Object> magicType =getMap(new KV("show",true),new KV("type",magicTypes));
        Map<String, Object> saveAsImage =getMap(new KV("show",true));
        feature.put("dataView",dataView);
        feature.put("magicType",magicType);
        feature.put("saveAsImage",saveAsImage);
        toolbox.put("feature",feature);
        option.put("toolbox",toolbox);
        //legend
        List<String> legendNames=getLegend(isHasYser,lines,data);
        Map<String, Object> legend =getMap(new KV("data",legendNames));
        option.put("legend",legend);
        //xAxis
        String x_col=conf.getX_col();
        //处理数据
        List<String> xNames=getXAxis(isHasYser,x_col,data);
        Map<String, Object> xAxis =getMap(new KV("type","category"));
        xAxis.put("data",xNames);
        Map<String, Object> axisPointer_x =getMap(new KV("type","shadow"));
        xAxis.put("axisPointer",axisPointer_x);
        List<Map<String, Object>> xAxisList=new ArrayList<>();
        xAxisList.add(xAxis);
        option.put("xAxis",xAxisList);
        //yAxis
        List<Map<String, Object>> yAxisList=new ArrayList<>();
        Map<String,Integer> yAxisMap = fullYAxisList(isHasYser,lines,yAxisList);
        option.put("yAxis",yAxisList);
        //series
        List<Map<String, Object>> series=getSeries(x_col,isHasYser,lines,legendNames,xNames,data,yAxisMap);
        option.put("series",series);
        return option;
    }
    private boolean isHasYser(List<DVConfLine> lines){
        for(DVConfLine line:lines){
            if("Y".equals(line.getData_type())){
                return true;
            }
        }
        return false;
    }
    private List<Map<String, Object>> getSeries(String x_col,boolean y,List<DVConfLine> lines,List<String> legend,List<String> xNames,List<Map<String,Object>> data,Map<String,Integer> yAxisMap){
        List<Map<String, Object>> series=new ArrayList<>();
        if(y){
            DVConfLine yline=null;
            for(DVConfLine line:lines){
                if("Y".equals(line.getData_type())){
                    yline=line;
                   break;
                }
            }
            //填充数据
            Map<String, Object> sery=new HashMap<>();
            sery.put("name",yline.getCol_zh());
            sery.put("type",yline.getType());
            List<Double> seryData=new ArrayList<>();
            for(Map<String,Object> row:data){
                Double val=getDouble(row,yline.getCol());
                seryData.add(val);
            }
            sery.put("data",seryData);
            series.add(sery);
        }else{
            Map<String,Double> keyMap=new HashMap<>();
            Map<String,String> legendTypeMap=new HashMap<>();//展示类别Map
            for(DVConfLine line:lines){
                String col=line.getCol();
                String y_col=line.getY_col();
                String type=line.getType();
                for(Map<String,Object> row:data){
                    String xValue=getString(row,x_col);
                    String tValue=getString(row,col);
                    Double yValue=getDouble(row,y_col);
                    String key=xValue+"-"+tValue;
                    keyMap.put(key,yValue);
                    legendTypeMap.put(tValue,type+"-"+y_col);
                }
            }
            for(String le:legend){
                Map<String, Object> sery=new HashMap<>();
                String typeMapValue=legendTypeMap.get(le);
                String[] type_ycol=typeMapValue.split("-");
                String type=type_ycol[0];
                String y_col=type_ycol[1];
                sery.put("name",le);
                sery.put("type",type);
                sery.put("yAxisIndex",yAxisMap.get(y_col));
                List<Double> seryData=new ArrayList<>();
                for(String x:xNames){
                    String key=x+"-"+le;
                    Double val=keyMap.get(key);
                    if(val==null){
                        val=0d;
                    }
                    seryData.add(val);
                }
                sery.put("data",seryData);
                series.add(sery);

            }
        }
        return series;

    }
    private Map<String,Integer> fullYAxisList(boolean y,List<DVConfLine> lines,List<Map<String, Object>> yAxisList){
        Map<String,Integer> yAxisMap=new HashMap<>();
        if(y){
            for(DVConfLine line:lines){
                if("Y".equals(line.getData_type())){
                    String col=line.getCol();
                    yAxisMap.put(col,0);
                    Map<String, Object> yAxis =getMap(new KV("type","value"),new KV("name","数值"));
                    yAxisList.add(yAxis);
                    break;
                }
            }
        }else{
            for(DVConfLine line:lines){
                int index=0;
                if("T".equals(line.getData_type())){
                    String col=line.getY_col();
                    if(yAxisMap.containsKey(col)){
                        continue;
                    }
                    yAxisMap.put(col,index);
                    Map<String, Object> yAxis =getMap(new KV("type","value"),new KV("name","数值"));
                    yAxisList.add(yAxis);
                    index++;
                }
            }
        }
        return  yAxisMap;
    }
    private List<String> getXAxis(boolean y,String x_col,List<Map<String,Object>> data){
        List<String> xAxis=new ArrayList<>();
        if(y){
            for(Map<String,Object> row:data){
                xAxis.add(getString(row,x_col));
            }
        }else{
            Set<String> set=new LinkedHashSet<>();
            for(Map<String,Object> row:data){
                set.add(getString(row,x_col));
            }
            for(String val:set){
                xAxis.add(val);
            }
        }
        return xAxis;
    }
    private List<String> getLegend(boolean y,List<DVConfLine> lines,List<Map<String,Object>> data){
        List<String> legend=new ArrayList<>();
        if(y){
            for(DVConfLine line:lines){
                if("Y".equals(line.getData_type())){
                    legend.add(line.getCol_zh());
                    break;
                }
            }
        }else{
            Set<String> set=new LinkedHashSet<>();
            for(DVConfLine line:lines){
                if("T".equals(line.getData_type())){
                    String col=line.getCol();
                    for(Map<String,Object> row:data){
                        set.add(getString(row,col));
                    }
                }
            }
            for(String val:set){
                legend.add(val);
            }
        }
        return legend;
    }
    private Map<String,Object> getMap(KV ... kvs){
        Map<String,Object> map=new HashMap<>();
        for(KV k:kvs){
            map.put(k.getKey(),k.getValue());
        }
        return map;
    }
    private class KV{
        private String key;
        private Object value;
        public KV(String key,Object value){
            this.key=key;
            this.value=value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
