package com.fanruan.platform.dao;

import com.fanruan.platform.bean.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleDao extends JpaRepository<Role,Integer> {

    /**角色列表*/
    Page<Role> findAll(Pageable pageable);
    List<Role> findAll();
    /**根据角色ID查询角色*/
    Optional<Role> findByRoleId(String roleId);
    Optional<Role> findByRoleName(String roleName);
}
