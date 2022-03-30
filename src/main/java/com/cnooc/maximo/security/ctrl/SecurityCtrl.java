package com.cnooc.maximo.security.ctrl;
/**
 * @ClassName SecurityCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年03月18日 14:21:00
 */

import com.cnooc.maximo.security.service.SecurityService;
import com.cnooc.platform.page.QueryCondition;
import com.cnooc.platform.util.json.JSONUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: vels
 * @description: 安全統計控制器
 * @author: TONG
 * @create: 2021-03-18 14:21
 **/
@RestController
@RequestMapping("/maximo/security")
@Api(tags = "Maximo安全统计")
public class SecurityCtrl {
    @Autowired
    private SecurityService securityService;
    @RequestMapping(value = "/equiCategoryRep.do",method = RequestMethod.POST)
    public String equiCategoryRep(@RequestBody QueryCondition condition){
        return JSONUtil.getJson(securityService.equiCategoryRep(condition));
    }
}
