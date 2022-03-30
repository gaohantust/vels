package com.cnooc.platform.exception.bean;

/**
 * @author Tong
 * @version V1.0
 * @ClassName LoginException.java
 * @Description TODO
 * @createTime 2020年12月01日 17:13:00
 */
public class LoginException extends BaseException {

    public LoginException(String key, String mes) {
        super(key, mes);
    }

    @Override
    public int getStatus() {
        return 402;
    }
}
