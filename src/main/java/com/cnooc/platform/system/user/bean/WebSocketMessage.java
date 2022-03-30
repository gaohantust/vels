package com.cnooc.platform.system.user.bean;/**
 * @ClassName WebSocketMessage.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月07日 11:23:00
 */

import com.cnooc.platform.system.user.domain.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: vels
 * @description: webSocket消息
 * @author: TONG
 * @create: 2021-01-07 11:23
 **/
public class WebSocketMessage {
    private String from;
    private String to;
    private String message;
    private Date date;
    private List<User> users=new ArrayList<>();
    //0 系统消息 1 用户消息
    private int type;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
