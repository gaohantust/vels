package com.cnooc.platform.datav.config.ctrl;
/**
 * @ClassName DVConfCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月31日 09:52:00
 */

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.datav.config.bean.DVConfLineBean;
import com.cnooc.platform.datav.config.bean.DVConfPieBean;
import com.cnooc.platform.datav.config.bean.DVConfSuper;
import com.cnooc.platform.datav.config.bean.DVConfTableBean;
import com.cnooc.platform.datav.config.domain.DVConf;
import com.cnooc.platform.datav.config.service.DVConfService;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: DVConf Ctrl
 * @author: TONG
 * @create: 2021-03-31 09:52
 **/
@RestController
@RequestMapping(value = "dvconf",produces = {"application/json;charset=UTF-8"})
public class DVConfCtrl extends BaseCtrl<DVConf> {
    @Autowired
    private DVConfService dvConfService;

    @Override
    public BaseService<DVConf> getService() {
        return dvConfService;
    }
    @RequestMapping("saveTable.do")
    public String saveTable(@RequestBody DVConfTableBean data){
        dvConfService.saveTable(data);
        return "";
    }
    @RequestMapping("savePie.do")
    public String savePie(@RequestBody DVConfPieBean data){
        dvConfService.savePie(data);
        return "";
    }
    @RequestMapping("saveLine.do")
    public String saveLine(@RequestBody DVConfLineBean data){
        dvConfService.saveLine(data);
        return "";
    }
    @RequestMapping("getConf.do")
    public String getConf(@RequestBody String id){
        DVConfSuper data= dvConfService.getConf(id);
        return JSONUtil.getJson(data);
    }
    @RequestMapping("getByCode.do")
    public String getByCode(@RequestBody String code){
        DVConfSuper data= dvConfService.getByCode(code);
        return JSONUtil.getJson(data);
    }
    @RequestMapping("getConfData/{id}.do")
    public String getConfData(@PathVariable("id") String id, @RequestBody QueryCondition condition){
       Object obj= dvConfService.getConfData(id,condition);
       return JSONUtil.getJson(obj);
    }
    @RequestMapping("createMenu.do")
    public String createMenu(@RequestBody String id){
        dvConfService.createMenu(id);
        return "";
    }
    @RequestMapping("getShareInfo.do")
    public String getShareInfo(@RequestBody String id){
        Map<String,Object> data=dvConfService.getShareInfo(id);
        return JSONUtil.getJson(data);
    }
    @RequestMapping("shareSave.do")
    public String shareSave(@RequestBody Map<String,Object> data){
        String id=data.get("id").toString();
        List<String> users=(ArrayList<String>)data.get("users");
        List<String> roles=(ArrayList<String>)data.get("roles");
        dvConfService.shareSave(id,users,roles);
        return "";
    }
}
