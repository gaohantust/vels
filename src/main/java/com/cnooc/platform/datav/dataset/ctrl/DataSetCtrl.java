package com.cnooc.platform.datav.dataset.ctrl;/**
 * @ClassName DataSetCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月29日 13:51:00
 */

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.dataset.domain.DataSet;
import com.cnooc.platform.datav.dataset.domain.DataSetCol;
import com.cnooc.platform.datav.dataset.service.DataSetService;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: vels
 * @description: 数据集Ctrl
 * @author: TONG
 * @create: 2021-03-29 13:51
 **/
@RestController
@RequestMapping(value = "dataset",produces = {"application/json;charset=UTF-8"})
public class DataSetCtrl extends BaseCtrl<DataSet> {
    @Autowired
    private DataSetService dataSetService;

    @Override
    public BaseService<DataSet> getService() {
        return dataSetService;
    }

    @RequestMapping("/load.do")
    public String load( @RequestBody String id){
        Object obj=dataSetService.load(id);
        return JSONUtil.getJson(obj);
    }
    @RequestMapping("/step/{step}.do")
    public String step(@PathVariable("step") int step, @RequestBody DataSet ds){
        Object obj=dataSetService.step(step,ds);
        return JSONUtil.getJson(obj);
    }
    @RequestMapping("/saveCol/{dsid}.do")
    public String saveCol(@PathVariable("dsid") String dsId,@RequestBody List<DataSetCol> cs){
        dataSetService.saveCol(dsId,cs);
        return JSONUtil.getJson(dsId);
    }
    @RequestMapping("/getCols.do")
    public String getCols( @RequestBody String id){
        List<DataSetCol> list=dataSetService.getCols(id);
        return JSONUtil.getJson(list);
    }
}
