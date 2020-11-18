package com.fanruan.platform.dao;

import com.fanruan.platform.bean.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

public interface AreaDao extends JpaRepository<Area,Integer> {
    Optional<Area> findById(Integer id);
    Optional<Area> findByAreaCode(String id);
    Optional<Area> findByAreaName(String areaName);
    List<Area> findAllByAreaLevel(String areaLevel);
    List<Area> findAll();
}
