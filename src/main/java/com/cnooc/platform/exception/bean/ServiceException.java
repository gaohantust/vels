package com.cnooc.platform.exception.bean;

/**
 * @author Tong
 * @version V1.0
 * @ClassName ServiceException.java
 * @Description TODO
 * @createTime 2020年12月01日 16:45:00
 */
public class ServiceException extends BaseException {

    public ServiceException(String key, String mes) {
        super(key, mes);
    }

    @Override
    public int getStatus() {
        return 450;
    }
}
