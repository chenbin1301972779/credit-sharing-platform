package com.fanruan.platform.dao;

import com.fanruan.platform.bean.User;
import com.fanruan.platform.bean.UserPermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPermissionDao extends JpaRepository<UserPermission,Integer> {
    List<UserPermission> findAllByUserIdAndPermissionStatus(Integer userId,String status);
    List<UserPermission> findAllByUserIdInAndPermissionStatus(List<Integer> userIds,String status);
    List<UserPermission> findAll();
    Page<UserPermission> findAll(Pageable pageable);
}
