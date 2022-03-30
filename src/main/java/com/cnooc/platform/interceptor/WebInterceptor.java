package com.cnooc.platform.interceptor;/**
 * @ClassName WebInterceptor.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月30日 11:02:00
 */

import com.cnooc.platform.exception.bean.AuthException;
import com.cnooc.platform.exception.bean.LoginException;
import com.cnooc.platform.security.SecurityMetadataSource;
import com.cnooc.platform.system.dic.service.DictionaryCacheService;
import com.cnooc.platform.system.log.domain.RequestLog;
import com.cnooc.platform.system.log.domain.RequestWrapper;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.util.Global;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: vels
 * @description: Web全局拦截器
 * @author: TONG
 * @create: 2020-12-30 11:02
 **/
public class WebInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(WebInterceptor.class);
    private static final ThreadLocal<RequestLog> startTimeThreadLocal = new NamedThreadLocal<RequestLog>("ThreadLocal StartTime");
    public WebInterceptor() {
        logger.info("===========系统安全管理器启动===========");
    }


    /**
     *在请求处理之前执行，主要用于权限验证、参数过滤等
     * @author TONG
     * @date 2020/12/30 11:38
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        String url = request.getServletPath();
        Long beginTime=System.currentTimeMillis();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        if("/user/login.do".equals(url)){
            body="*****";
        }
        RequestLog requestLog=new RequestLog();
        requestLog.setBegin(beginTime);
        requestLog.setParam(body);
        requestLog.setUrl(url);
        startTimeThreadLocal.set(requestLog); //线程绑定变量（该数据只有当前请求的线程可见）
        List<Resource> needRes = SecurityMetadataSource.getResource(url);
        logger.debug("当前请求==>"+url);
        boolean isHasAuth=false;
        if(needRes.size()==0){
            return true;
        }
        LoginInfo loginInfo = getLoginInfo(request);
        for(Resource res: needRes){
            logger.debug("需要权限==>"+ res.getCode() );
            if (loginInfo.isHasAuth(res)) {
                isHasAuth= true;
                break;
            }
        }
        if(isHasAuth){
            return true;
        }else {
            return true;
            //throw new AuthException("","没有访问权限");
        }
    }
    /**
     *当前请求进行处理之后执行，主要用于日志记录、权限检查、性能监控、通用行为等
     * @param request
     * @param response
     * @param obj
     * @param modelAndView
     * @author TONG
     * @date 2020/12/30 11:38
     * @return void
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj,
                           ModelAndView modelAndView) throws Exception {

    }
    /**
     *当前对应的interceptor的perHandle方法的返回值为true时,postHandle执行完成并渲染页面后执行，主要用于资源清理工作
     * @param request
     * @param response
     * @param obj
     * @param exception
     * @author TONG
     * @date 2020/12/30 11:39 
     * @return void
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj,
                                Exception exception) throws Exception {
        RequestLog requestLog= startTimeThreadLocal.get(); //得到线程绑定的局部变量
        long beginTime=requestLog.getBegin();
        long endTime = System.currentTimeMillis(); //结束时间
        long times=endTime-beginTime;
        requestLog.setBegin(times);
        String method=obj.toString();
        requestLog.setMethod(method);
        SecurityMetadataSource.logService.saveLog(request,requestLog);
    }


    private LoginInfo getLoginInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object info = session.getAttribute(Global.CUR_USER);
        if (!(info instanceof LoginInfo)) {
            throw new LoginException("","Session失效,需要重新登录");
        }
        LoginInfo loginInfo = (LoginInfo) info;
        if (loginInfo == null || loginInfo.getUser() == null)
            throw new LoginException("","Session失效,需要重新登录");
        return loginInfo;
    }
}
