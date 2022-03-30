package com.cnooc.platform.system.resource.domain;

import com.cnooc.platform.core.ArchiveEntity;
import com.cnooc.platform.core.TreeEntity;
import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**

/**
 * @author Tong
 * @version V1.0
 * @ClassName Resource.java
 * @Description TODO
 * @createTime 2020年12月01日 14:00:00
 */
@Entity
@Table(name="SYS_RESOURCE")
public class Resource extends TreeEntity<Resource> {
    @Column(nullable = true)
    @RefJsonWrite
    private String url;
    @Column(nullable = false)
    private String type;
    @Column(nullable = true)
    @RefJsonWrite
    private String view_path;
    @Column(nullable = true)
    @org.hibernate.annotations.Type(type = "yes_no")
    private boolean part = false;
    @Column(nullable = true)
    @org.hibernate.annotations.Type(type = "yes_no")
    @RefJsonWrite
    private boolean dv = false;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getView_path() {
        return view_path;
    }

    public void setView_path(String view_path) {
        this.view_path = view_path;
    }

    public boolean isPart() {
        return part;
    }

    public void setPart(boolean part) {
        this.part = part;
    }

    public boolean isDv() {
        return dv;
    }

    public void setDv(boolean dv) {
        this.dv = dv;
    }
}
