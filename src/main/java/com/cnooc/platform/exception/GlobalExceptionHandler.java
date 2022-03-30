package com.cnooc.platform.exception;/**
 * @ClassName GlobalExceptionHandler.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月01日 17:10:00
 */

import com.cnooc.platform.exception.bean.BaseException;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName GlobalExceptionHandler.java
 * @Description TODO
 * @createTime 2020年12月01日 17:10:00
 */
@ControllerAdvice
@Priority(1)
public class GlobalExceptionHandler {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public  GlobalExceptionHandler(){
        System.out.println("===========异常拦截器已启动=============");
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception){
        log.error(exception.getMessage());
        if(exception instanceof BaseException){
            BaseException ex = (BaseException) exception;
            response.setStatus(ex.getStatus());
            Map<String,String> map=new HashMap<String,String>();
            map.put("key",ex.getKey());
            map.put("mes",ex.getMes());
            return JSONUtil.getJson(map);
        }else {

            exception.printStackTrace();
            response.setStatus(500);
            Map<String,String> map=new HashMap<String,String>();
            map.put("mes",exception.getMessage());
            return JSONUtil.getJson(map);
        }
    }
}
