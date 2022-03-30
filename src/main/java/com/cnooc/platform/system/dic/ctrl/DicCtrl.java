package com.cnooc.platform.system.dic.ctrl;/**
 * @ClassName DicCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月21日 08:51:00
 */

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.page.Filter;
import com.cnooc.platform.system.dic.domain.Dic;
import com.cnooc.platform.system.dic.service.DicService;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: vels
 * @description: 数据字典控制器
 * @author: TONG
 * @create: 2020-12-21 08:51
 **/
@RestController
@RequestMapping("/dic")
public class DicCtrl extends BaseCtrl<Dic> {
    @Autowired
    private DicService dicService;
    @Override
    public BaseService<Dic> getService() {
        return dicService;
    }

    @RequestMapping("/findByGroup.do")
    @ResponseBody
    public String findByParentCode(@RequestBody String code) {
        return JSONUtil.getJson(dicService.findByParentCode(code));
    }
    @RequestMapping("/findEntity/{table}/{query}.do")
    @ResponseBody
    public String findByTable(@PathVariable("table") String table, @PathVariable("query") String query,@RequestBody List<Filter> filters) {
        return JSONUtil.getJson(dicService.findByTable(table,query,filters));
    }
    @RequestMapping("/findByParent.do")
    @ResponseBody
    public String findByParent(@RequestBody String id) {
        return JSONUtil.getJson(dicService.findByParent(id));
    }


}
