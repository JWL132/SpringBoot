package com.my.hello.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.my.hello.dataobject.OrderDetail;
import com.my.hello.utils.seriallizer.Date2longSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据传输对象,在各个层传输用
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单Id*/
    private  String orderId;

    /** 买家姓名*/
    private  String buyerName;

    /** 买家电话*/
    private  String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家微信openid*/
    private String buyerOpenid;

    /** 订单总金额*/
    private BigDecimal orderAmount;

    /** 订单状态,默认为0新订单*/
    private Integer orderStatus;

    /** 支付状态，默认为0未支付*/
    private  Integer payStatus;

    /** 创建时间*/
    @JsonSerialize(using = Date2longSerializer.class)
    private Date createTime;

    /** 更新时间*/
    @JsonSerialize(using = Date2longSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;




}
