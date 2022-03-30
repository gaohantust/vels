package com.cnooc.platform.system.resource.ctrl;/**
 * @ClassName ResourceCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月01日 14:17:00
 */


import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.resource.service.ResourceService;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tong
 * @version V1.0
 * @ClassName ResourceCtrl.java
 * @Description TODO
 * @createTime 2020年12月01日 14:17:00
 */
@RestController
@RequestMapping("res")
public class ResourceCtrl extends BaseCtrl<Resource> {
    @Autowired
   private ResourceService resourceService;
    @Override
    public BaseService<Resource> getService() {
        return resourceService;
    }
    @RequestMapping("list.do")
    public String list(){
        return JSONUtil.getJson(resourceService.getMenuList());
    }
}
