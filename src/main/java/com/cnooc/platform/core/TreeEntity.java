package com.cnooc.platform.core;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * @ClassName: TreeEntity
 * @Description: TODO 树结构实体基类
 * @author LZ.T
 * @date 2016-11-26 下午11:55:22
 * @version V2.0
 */
@MappedSuperclass
public class TreeEntity<T> extends ArchiveEntity {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent", nullable = true)
	private T parent;

	@Column(nullable = true, precision = 4)
	private int levels = 0;
	@Column(nullable = true, precision = 4)
	private int sort_no;

	@Column(nullable = false)
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean leafe = false;

	@Column(nullable = true, length = 30)
	private String icon;

	public boolean isLeafe() {
		return leafe;
	}

	public void setLeafe(boolean leafe) {
		this.leafe = leafe;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public int getSort_no() {
		return sort_no;
	}

	public void setSort_no(int sort_no) {
		this.sort_no = sort_no;
	}
	

}
