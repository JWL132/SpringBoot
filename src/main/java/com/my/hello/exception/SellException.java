package com.my.hello.exception;

import com.my.hello.enums.ResultEnum;

/**
 * 处理异常类
 */
public class SellException extends RuntimeException{

    private  Integer code;

    public SellException(ResultEnum resultEnum) {
        /**
         * 调用了ResultEnum中的Message（商品不存在信息）
         */
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {

        super(message);
        this.code = code;
    }

}
