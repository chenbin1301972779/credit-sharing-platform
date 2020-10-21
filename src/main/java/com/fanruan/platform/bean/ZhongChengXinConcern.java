package com.fanruan.platform.bean;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "LOG_ZCX_CONCERN")
@IdClass(ZcxConcernPrimaryKey.class)
public class ZhongChengXinConcern {

    @Id
    @Column(name="CODE")
    private String code;

    @Column(name="COUNTYNAME")
    private String countyName;

    @Column(name="COUNTYCODE")
    private String countyCode;

    @Column(name="CITYCODE")
    private String cityCode;

    @Column(name="CITYNAME")
    private String cityName;

    @Column(name="PROVINCECODE")
    private String provinceCode;

    @Column(name="PROVINCENAME")
    private String provinceName;

    @Column(name="AREALEVEL")
    private String areaLevel;

    @Column(name="ENTTYPE")
    private String entType;

    @Column(name="ENTNAME")
    private String entName;

    @Column(name="REQUESTID")
    private String requestId;

    //TODO 设置联合主键
    @Id
    @Column(name="UPDATEBY")
    private String updateBy;

    @Column(name="UPDATETIME")
    private Timestamp updateTime;

    @Column(name="CONCERN_FLAG")
    private String concernFlag;
}
