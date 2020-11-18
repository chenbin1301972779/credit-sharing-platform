package com.fanruan.platform.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * 公司基本信息，保存oracle，基于用户名称和曾用名在es中建立索引
 */
@Data
@Entity
@Table(name = "credit_company_extend")
public class CompanyExtendInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "extend_seq")
    @SequenceGenerator(name = "extend_seq", initialValue = 1, allocationSize = 1, sequenceName = "EXTEND_SEQUENCE")
    private Integer id;

    @Column(name="cid")
    private Integer cid;
    /**
     * 公司名称
     */
    @Column(name="company_name")
    private String companyName;



    /**
     * 社会统一信用编码
     */
    @Column(name="creditCode")
    private String creditCode;


    /**
     * 数据来源
     */
    @Column(name="source_type")
    private String sourceType;

    /**
     * 接口json数据
     */
    @Lob @Basic(fetch=FetchType.LAZY)
    @Column(name="extend_info")
    private String extendInfo;

    /**
     * 用户id
     */
    @Column(name="UPDATEBY")
    private Integer updateby;



    /**
     * 数据更新时间
     */
    @Column(name="update_time")
    private Timestamp updateTime;

}
