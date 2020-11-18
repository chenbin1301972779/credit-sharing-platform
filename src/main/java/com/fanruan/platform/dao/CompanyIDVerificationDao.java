package com.fanruan.platform.dao;

import com.fanruan.platform.bean.CompanyIDVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyIDVerificationDao extends JpaRepository<CompanyIDVerification,Integer> {
    CompanyIDVerification findByCode(String code);
}
