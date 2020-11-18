package com.fanruan.platform.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户和权限点之间的关联关系
 */
@Data
@Entity
@Table(name = "credit_user_permission")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_permission_seq")
    @SequenceGenerator(name = "user_permission_seq", initialValue = 1, allocationSize = 1, sequenceName = "USER_PERMISSION_SEQUENCE")
    private Integer id;
    /**
     * 用户id
     */
    @Column(name="user_id")
    private Integer userId;
    /**
     * 权限级别
     */
    @Column(name="permission_role")
    private String permissionRole;

    @Column(name="permission_status")
    private String permissionStatus;

    @Column(name="ROLE_NAME")
    private String roleName;

    @Column(name="update_time")
    private Timestamp updateTime;

    @Column(name="update_by")
    private String updateBy;

}
