package com.cnooc.platform.config;
/**
 * @ClassName WebSocketServer.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月06日 16:18:00
 */

import com.alibaba.fastjson.JSONObject;
import com.cnooc.platform.system.user.bean.WebSocketBean;
import com.cnooc.platform.system.user.bean.WebSocketMessage;
import com.cnooc.platform.system.user.domain.LoginInfo;
import com.cnooc.platform.system.user.domain.User;
import com.cnooc.platform.system.user.service.UserService;
import com.cnooc.platform.util.Global;
import com.cnooc.platform.util.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: vels
 * @description: WS服务
 * @author: TONG
 * @create: 2021-01-06 16:18
 **/
@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {
    @Autowired
    private UserService userService;
    public static WebSocketServer webSocketServer;

    @PostConstruct
    public void init() {
        webSocketServer = this;
        webSocketServer.userService = this.userService;
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象。
    private static ConcurrentHashMap<String, WebSocketBean> sessionPools = new ConcurrentHashMap<>();

    //发送消息
    public void sendMessage(Session session, WebSocketMessage message) throws IOException, EncodeException {
        if (session != null) {
            synchronized (session) {
                message.setUsers(getOnline());
                session.getBasicRemote().sendText(JSONUtil.getJson(message));
            }
        }
    }

    //给指定用户发送信息
    public void sendInfo(String userCode, WebSocketMessage message) {
        try {
            for (String key : sessionPools.keySet()) {
                if (key.startsWith(userCode + "-")) {
                    Session session = sessionPools.get(key).getSession();
                    sendMessage(session, message);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String sid) {
        String[] array = sid.split("-");
        String code = array[0];
        User user = webSocketServer.userService.findByCode(code);
        WebSocketBean bean = new WebSocketBean(session, user);
        sessionPools.put(sid, bean);
        try {
            WebSocketMessage message = new WebSocketMessage();
            message.setDate(new Date());
            message.setFrom("系统消息");
            message.setType(0);
            message.setMessage("欢迎" + user.getName() + "登录！");
            sendMessage(session, message);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName) {
        sessionPools.remove(userName);
    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message) throws IOException, EncodeException {
        WebSocketMessage mes = JSONObject.parseObject(message, WebSocketMessage.class);
        mes.setDate(new Date());
        String to = mes.getTo();
        for (String key : sessionPools.keySet()) {
            if (key.startsWith(to + "-")) {
                Session session = sessionPools.get(key).getSession();
                sendMessage(session, mes);
            }
        }
    }

    private List<User> getOnline() {
        List<User> users = new ArrayList<>();
        Set<String> set=new HashSet<>();
        for (String key : sessionPools.keySet()) {

            WebSocketBean bean = sessionPools.get(key);
            User user=bean.getUser();
            if(set.contains(user.getId())){
                continue;
            }
            set.add(user.getId());
            users.add(user);
        }
        return users;
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

}
