package com.cnooc.platform.system.role.domain;/**
 * @ClassName Role.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2020年12月24日 16:17:00
 */

import com.cnooc.platform.core.ArchiveEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @program: vels
 * @description: 用户角色实体
 * @author: TONG
 * @create: 2020-12-24 16:17
 **/
@Entity
@Table(name="SYS_ROLE")
public class Role extends ArchiveEntity {

}
