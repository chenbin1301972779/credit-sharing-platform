package com.fanruan.platform.bean;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p>TODO</p>
 *
 * @author DELk
 * @@version 1.0.0
 * @since 2020/10/18
 */
@Data
@Entity
@Table(name = "LOG_INTERFACE_REPORT")
public class RecordInterfaceCall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOKEN_ID")
    private String tokenId;

    @Column(name = "REQUEST_MESSAGE")
    private String requestMessage;

    @Column(name = "URI")
    private String uri;

    @Column(name = "REQUEST_TIME")
    private String requestTime;
}
