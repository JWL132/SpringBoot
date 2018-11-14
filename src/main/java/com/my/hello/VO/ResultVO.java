package com.my.hello.VO;

import lombok.Data;

/**
 * 视图层
 */
@Data
public class ResultVO<T> {

    /** 错误码*/
    private  Integer code;

    /** 提示信息*/
    private String msg;

    /** 具体内容*/
    private  T data;

    public ResultVO() {
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
