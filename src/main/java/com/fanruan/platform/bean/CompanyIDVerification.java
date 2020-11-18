package com.fanruan.platform.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "INPUT_HR_ORG")
public class CompanyIDVerification {
    @Id
    @Column(name="CODE")
    private String code;
    @Column(name="NAME")
    private String name;
    @Column(name="RULE")
    private String rule;
}
