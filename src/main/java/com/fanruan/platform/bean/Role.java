package com.fanruan.platform.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "CREDIT_USER_ROLE")
public class Role {
    /**角色的编号*/
    @Id
    @Column(name="ROLE_ID")
    private String roleId;
    /**角色的名称*/
    @Column(name="ROLE_NAME")
    private String roleName;
    /**角色的权限*/
    @Column(name="PERMISSION")
    private String permission;
}
