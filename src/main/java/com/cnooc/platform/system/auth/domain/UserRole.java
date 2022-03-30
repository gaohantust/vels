package com.cnooc.platform.system.auth.domain;


import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.role.domain.Role;
import com.cnooc.platform.system.user.domain.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


//用户角色
@Entity
@Table(name = "SYS_USER_ROLE")
public class UserRole extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "sys_user", nullable = true)
	private User user;

	@ManyToOne
	@JoinColumn(name = "role", nullable = false)
	private Role role;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
