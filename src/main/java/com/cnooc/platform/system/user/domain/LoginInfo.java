package com.cnooc.platform.system.user.domain;

import com.cnooc.platform.system.resource.domain.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Tong
 * @version V1.0
 * @ClassName LoginInfo.java
 * @Description TODO
 * @createTime 2020年12月02日 11:15:00
 */
public class LoginInfo {
    public LoginInfo(User user) {
        this.user = user;
    }

    private User user;
    private List<Map<String, Object>> resource = new ArrayList<>();
    private List<Map<String, Object>> routes = new ArrayList<>();
    private List<String> auth = new ArrayList<>();
    private Map<String,Object> client=new HashMap<>();
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Map<String, Object>> getResource() {
        return resource;
    }

    public void setResource(List<Map<String, Object>> resource) {
        this.resource = resource;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getAuth() {
        return auth;
    }

    public void setAuth(List<String> auth) {
        this.auth = auth;
    }

    public List<Map<String, Object>> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Map<String, Object>> routes) {
        this.routes = routes;
    }

    public Map<String, Object> getClient() {
        return client;
    }

    public void setClient(Map<String, Object> client) {
        this.client = client;
    }

    public boolean isHasAuth(Resource needRes ){
        if(needRes==null){
            return true;
        }
        String code=needRes.getCode();
        return auth.contains(code);
    }
    public LoginInfo toSimple(){
        LoginInfo loginInfo=new LoginInfo(getUser());
        loginInfo.setClient(getClient());
        loginInfo.setToken(getToken());
        return loginInfo;
    }
}
