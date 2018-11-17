package com.my.hello.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足,请稍后下单"),
    ORDER_NOT_EXIT(12,"订单不存在"),
    ORDERDETAIL_NOT_EXIT(13,"订单详情不存在"),
    ORDER_STATUS_ERRROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"取消订单失败"),
    ORDER_DETAIL_EMPTY(16,"该订单详情为空"),
    ORDER_DETAIL_FINSH(17,"订单未支付"),
    ORDER_DETAIL_FINSH1(18,"订单支付成功，请您完结订单"),

    PARAM_ERROR(19,"参数不正确"),
    ;
    private  Integer code;

    private  String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
