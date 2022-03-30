package com.cnooc.platform.system.log.service;
/**
 * @ClassName LogService.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月30日 14:07:00
 */

import com.cnooc.platform.core.BaseDao;
import com.cnooc.platform.core.BaseService;
import com.cnooc.platform.exception.bean.LoginException;
import com.cnooc.platform.security.SecurityMetadataSource;
import com.cnooc.platform.system.log.dao.LogDao;
import com.cnooc.platform.system.log.domain.Log;
import com.cnooc.platform.system.log.domain.RequestLog;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.util.Global;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: vels
 * @description: 操作日志服务
 * @author: TONG
 * @create: 2020-12-30 14:07
 **/
@Service
public class LogService extends BaseService<Log> {
    @Autowired
    private LogDao logDao;

    @Override
    public BaseDao<Log> getDao() {
        return logDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLog(HttpServletRequest request, RequestLog requestLog) {
        try {
            LoginInfo info = getLoginInfo();
            if (info == null) {
                return;
            }
            Map<String,String> osInfo=getOsAndBrowserInfo();
            User user = info.getUser();
            String url = request.getServletPath();
            String userCode = user.getCode();
            String userName = user.getName();
            Log log = new Log();
            log.setTimes(requestLog.getBegin());
            log.setUrl(requestLog.getUrl());
            log.setParam(requestLog.getParam());
            log.setMethod(requestLog.getMethod());
            log.setUser_code(userCode);
            log.setUser_name(userName);
            log.setIp(getRemoteAddress());
            log.setOs(osInfo.get("os"));
            log.setBrowser(osInfo.get("browser"));
            List<Resource> list = SecurityMetadataSource.getResource(url);
            Resource needRes= null;
            if(list.size()>0){
                needRes=list.get(0);
            }
            if(needRes!=null){
                log.setRes_code(needRes.getCode());
                log.setRes_name(needRes.getName());
                String desc="";
                if(needRes.getParent()==null){
                    desc=needRes.getName();
                }else{
                    desc=needRes.getParent().getName()+"/"+needRes.getName();
                }
                log.setDes(desc);
            }
            create(log);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private LoginInfo getLoginInfo() {
        Object info = getSession().getAttribute(Global.CUR_USER);
        if (info == null) {
            return null;
        }
        if (info instanceof LoginInfo) {
            return (LoginInfo) info;
        }
        return null;
    }


}
