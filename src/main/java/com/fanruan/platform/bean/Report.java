package com.fanruan.platform.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "credit_report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_t_seq")
    @SequenceGenerator(name = "report_t_seq", initialValue = 1, allocationSize = 1, sequenceName = "REPORT_T_SEQUENCE")
    private Integer reportId;

    /**
     * 文件名称
     */
    @Column(name="ORDER_ID")
    private String orderId;


    /**
     * 文件名称
     */
    @Column(name="FILE_NAME")
    private String fileName;


    /**
     * 文件名称
     */
    @Column(name="PDF_NAME")
    private String pdfName;

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
     * 报告类型
     */
    @Column(name="report_type")
    private String reportType;


    /**
     * 申请时间
     */
    @Column(name="UPDATE_TIME")
    private Timestamp updateTime;

    /**
     * 申请时间
     */
    @Column(name="UPDATE_BY")
    private String updateBy;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="report_html", columnDefinition="blob", nullable=true)
    @JsonIgnore
    private byte[] reportHtml;

    /**
     * 保存接口参数 AREA、INDUSTRY、COMPANY_TYPE
     */
    @Column(name="AREA")
    private String area;

    @Column(name="INDUSTRY")
    private String industry;

    @Column(name="COMPANY_TYPE")
    private String companyType;
}
