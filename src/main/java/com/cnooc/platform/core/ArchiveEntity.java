package com.cnooc.platform.core;

import com.cnooc.platform.util.json.annotation.RefJsonWrite;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 *
 * @param
 * @author TONG
 * @date 2021/3/26 11:41 
 * @return
 */
@MappedSuperclass
public class ArchiveEntity extends BaseEntity {
	@Column(nullable = true)
	@RefJsonWrite
	private String code;
	@Column(nullable = true)
	@RefJsonWrite
	private String name;
	/**
	 * 是否有效
	 */
	@Column(nullable = true)
	@org.hibernate.annotations.Type(type = "yes_no")
	private Boolean valid = true;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
}
