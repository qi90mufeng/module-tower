package com.albert.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 错误码
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum BEErrors {
    /*编码四位数*/
    SUCCESS("0000", "请求成功"),
    /*公用错误码*/
    PARAMS_ERROR("1001", "参数校验错误"),
    ILLEGAL_PARAM("1002", "非法入参!"),
    UNKNOW_ERROR("1003", "未知异常"),
    //停车场业务
    NO_CARPARK_DATA("2001", "该停车场不存在"),
    ;

    private String code;
    private String desc;
}
