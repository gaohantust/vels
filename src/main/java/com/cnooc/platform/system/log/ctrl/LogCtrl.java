package com.cnooc.platform.system.log.ctrl;
/**
 * @ClassName LogCtrl.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月30日 14:09:00
 */

import com.cnooc.platform.core.BaseCtrl;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.system.log.domain.Log;
import com.cnooc.platform.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: vels
 * @description: 操作日志Ctrl
 * @author: TONG
 * @create: 2020-12-30 14:09
 **/
@RestController
@RequestMapping("/log")
public class LogCtrl extends BaseCtrl<Log> {
    @Autowired
    private LogService logService;

    @Override
    public BaseService<Log> getService() {
        return logService;
    }
}
