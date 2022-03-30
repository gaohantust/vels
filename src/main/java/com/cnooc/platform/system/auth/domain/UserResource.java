package com.cnooc.platform.system.auth.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.user.domain.User;

@Entity
@Table(name = "SYS_USER_RES")
public class UserResource extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "sys_user", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "res", nullable = false)
	private Resource res;
	// 来源类别:role,通过角色赋的权限
	// auth:通过选择权限进行赋值
	@Column(nullable = false, length = 4)
	private String from_type;
	// roleID
	@Column(length = 32)
	private String from_role_id;

	public String getFrom_type() {
		return from_type;
	}

	public void setFrom_type(String from_type) {
		this.from_type = from_type;
	}

	public String getFrom_role_id() {
		return from_role_id;
	}

	public void setFrom_role_id(String from_role_id) {
		this.from_role_id = from_role_id;
	}

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
}
