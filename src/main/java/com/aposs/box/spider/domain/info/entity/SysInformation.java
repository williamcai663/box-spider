package com.aposs.box.spider.domain.info.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Table(name = "sys_information")
public class SysInformation {
    /**
     * 主键id,自增
     */
    @Column(name = "id")
    private Long id;
    /**
     * 1 企业 2 行业
     */
    @Column(name = "info_type")
    private int infoType;

    private String title;

    private String titleImgUrl;

    private String summary;

    private String spiderId;

    private String spiderUrl;

    private String sourceName;

    private Timestamp releaseTime;

    private String content;





}
