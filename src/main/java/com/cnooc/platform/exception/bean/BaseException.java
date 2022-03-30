package com.cnooc.platform.exception.bean;

/**
 * @author Tong
 * @version V1.0
 * @ClassName IException.java
 * @Description TODO
 * @createTime 2020年12月01日 17:07:00
 */
public abstract class BaseException extends RuntimeException{
    private String key;
    private String mes;
    public BaseException(String key,String mes) {
        super(mes);
        this.key=key;
        this.mes=mes;
    }

    public String getKey() {
        return key;
    }

    public String getMes() {
        return mes;
    }

    public abstract int getStatus() ;
}
