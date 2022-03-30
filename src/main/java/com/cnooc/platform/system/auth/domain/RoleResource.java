package com.cnooc.platform.system.auth.domain;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cnooc.platform.core.BaseEntity;
import com.cnooc.platform.system.resource.domain.Resource;
import com.cnooc.platform.system.role.domain.Role;

@Entity
@Table(name="SYS_ROLE_RES")
public class RoleResource extends BaseEntity {
	@ManyToOne
	@JoinColumn(name="role",nullable=false)
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="res",nullable=false)
	private Resource res;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Resource getRes() {
		return res;
	}
	public void setRes(Resource res) {
		this.res = res;
	}
}
