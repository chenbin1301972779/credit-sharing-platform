package com.fanruan.platform.bean;

import lombok.Data;

import java.io.Serializable;


/**
 * <p>
 *     中诚信联合主键
 * </p>
 *
 * @author DELk
 * @@version 1.0.0
 * @since 2020/10/13
 */
@Data
public class ZcxConcernPrimaryKey implements Serializable {
    private String code;
    private String updateBy;
}
