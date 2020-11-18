package com.fanruan.platform.dao;

import com.fanruan.platform.bean.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User,Integer> {
    Optional<User> findById(Integer id);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPasswordAndStatus(String username,String password,Integer status);
    Optional<User> findByUsernameAndStatus(String username,Integer status);
    Page<User> findAllByUsernameLikeAndNameLikeAndStatusInOrderByStatusDesc(Pageable pageable,String username,String name,List<Integer> status);
    Page<User> findAllByUsernameLikeAndStatusInOrderByStatusDesc(Pageable pageable,String username,List<Integer> status);
    Page<User> findAllByNameLikeAndStatusInOrderByStatusDesc(Pageable pageable,String name,List<Integer> status);
    Page<User> findAllByStatusInOrderByStatusDesc(Pageable pageable,List<Integer> status);
    Page<User> findAll(Pageable pageable);
    List<User> findAllByUsernameIn(List<String> usernameList);
    List<User> findAllByRoleNameLike(String roleName);
}
