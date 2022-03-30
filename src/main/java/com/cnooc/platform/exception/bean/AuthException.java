package com.cnooc.platform.exception.bean;

import com.fasterxml.jackson.databind.ser.Serializers;

/**
 * @author Tong
 * @version V1.0
 * @ClassName AuthException.java
 * @Description TODO
 * @createTime 2020年12月01日 17:19:00
 */
public class AuthException extends BaseException {

    public AuthException(String key, String mes) {
        super(key, mes);
    }

    @Override
    public int getStatus() {
        return 403;
    }
}
