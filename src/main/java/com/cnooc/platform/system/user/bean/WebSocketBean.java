package com.cnooc.platform.system.user.bean;
/**
 * @ClassName WebSocketBean.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月07日 11:21:00
 */

import com.cnooc.platform.system.user.domain.User;

import javax.websocket.Session;

/**
 * @program: vels
 * @description: webSocket对象
 * @author: TONG
 * @create: 2021-01-07 11:21
 **/
public class WebSocketBean {
    public WebSocketBean(){

    }
    public WebSocketBean(Session session,User user){
        this.session=session;
        this.user=user;
    }
    private User user;
    private Session session;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
