package com.frank.jsoup.test.vo;

import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author cy
 * @version ProxyIpVO.java, v 0.1 2020年06月23日 13:49 cy Exp $
 */
@Data
public class ProxyIpVO {

    private String ip;

    private Integer port;

    private String serverAddress;

    private String type;

    private Integer speed;

    private Integer connectionTime;

    private Integer aliveDays;

    private Date gmtPublished;

    private Integer statusEnum;

    private Date gmtChecked;
}