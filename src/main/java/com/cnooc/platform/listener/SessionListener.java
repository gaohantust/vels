package com.cnooc.platform.listener;
/**
 * @ClassName SessionListener.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月06日 08:42:00
 */

import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.util.Global;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.*;

/**
 * @program: vels
 * @description: Session监听器
 * @author: TONG
 * @create: 2021-01-06 08:42
 **/
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        ServletContext application = session.getServletContext();
        Object obj=application.getAttribute(Global.ONLINE_USER_KEY);
        if(obj!=null){
            Map<String,LoginInfo> users=(Map<String,LoginInfo>)obj;
            users.remove(session.getId());
            application.setAttribute(Global.ONLINE_USER_KEY,users);
        }
    }
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        HttpSession session = httpSessionBindingEvent.getSession();
        ServletContext application = session.getServletContext();
        String name=httpSessionBindingEvent.getName();
        if(Global.CUR_USER.equals(name)){
            Map<String,LoginInfo> users=new HashMap<>();
            Object obj=application.getAttribute(Global.ONLINE_USER_KEY);
            if(obj!=null){
                users=(Map<String,LoginInfo>)obj;
            }
            users.put(session.getId(),((LoginInfo)httpSessionBindingEvent.getValue()).toSimple());
            application.setAttribute(Global.ONLINE_USER_KEY,users);
        }
    }

}
