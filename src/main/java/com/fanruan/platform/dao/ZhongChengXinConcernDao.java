package com.fanruan.platform.dao;

import com.fanruan.platform.bean.TianYanChaConcern;
import com.fanruan.platform.bean.ZhongChengXinConcern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ZhongChengXinConcernDao extends JpaRepository<ZhongChengXinConcern,Integer> {
    Optional<ZhongChengXinConcern> findByCode(String code);
    Optional<ZhongChengXinConcern> findByCodeAndUpdateBy(String code, String updateBy);
    List<ZhongChengXinConcern> findAll();
}
