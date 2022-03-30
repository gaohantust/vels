package com.cnooc.platform.system.user.domain;
/**
 * @ClassName UserPart.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月14日 09:09:00
 */

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.resource.domain.Resource;

import javax.persistence.*;

/**
 * @program: vels
 * @description: 用户部件记录
 * @author: TONG
 * @create: 2021-01-14 09:09
 **/
@Entity
@Table(name="SYS_USER_PART")
public class UserPart  extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sys_user", nullable = true)
    private User user;
    @ManyToOne
    @JoinColumn(name="res",nullable=false)
    private Resource res;
    @Column()
    private int h;
    @Column()
    private int i;
    @Column()
    private int w;
    @Column()
    private int x;
    @Column()
    private int y;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resource getRes() {
        return res;
    }

    public void setRes(Resource res) {
        this.res = res;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
