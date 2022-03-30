package com.cnooc.platform.system.log.domain;
/**
 * @ClassName RequestLog.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月31日 09:27:00
 */

/**
 * @program: vels
 * @description: 请求日志信息
 * @author: TONG
 * @create: 2020-12-31 09:27
 **/
public class RequestLog {
    private String param;
    private Long begin;
    private String url;
    private String method;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
