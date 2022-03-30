package com.cnooc.platform.core;

import com.alibaba.druid.util.StringUtils;
import com.cnooc.platform.config.Global;
import com.cnooc.platform.util.json.JSONUtil;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Description:TODO 所有实体的基类
 * author TONG
 * date: 2020年3月6日 下午9:22:27
 */
@MappedSuperclass
public class BaseEntity {
    // 实体的id,由父类生成,采用UUID的方式实现
    @Id
    @Column()
    @RefJsonWrite
    private String id;
    // 实体的版本,实现并发控制
    @Version
    @Column()
    @RefJsonWrite
    private int version;
    //实创建时间
    @Temporal(TemporalType.TIMESTAMP)
    @Column()
    private Date create_date;
    //创建用户
    @Column()
    private String create_user;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }
    @PrePersist
    private void entityBeforeInsert() {
       setCreate_date(new Date());
       setCreate_user(getUserName());
    }

    private String getUserName() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute(Global.CUR_USER);
        return "SYSTEM";
    }
    public boolean isNew(){
        return StringUtils.isEmpty(getId());
    }
    protected String toJSON(){
        return JSONUtil.getJson(this);
    }
}
