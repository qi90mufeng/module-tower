package com.albert.global;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultBean<T> implements Serializable {

    private static final String SUCCESS = "0000";
    private static final String UN_KNOWS = "5002";
    /**
     * 响应码.
     */
    private String code;
    /**
     * 响应描述
     */
    private String msg;
    /**
     * 业务数据.
     */
    private T data;

    public ResultBean(String code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBean(BEErrors errors, T data) {
        super();
        this.code = errors.getCode();
        this.msg = errors.getDesc();
        this.data = data;
    }

    //----------------------静态方法----------------------
    public static ResultBean success() {
        return new ResultBean(SUCCESS, "请求成功", null);
    }

    public static ResultBean error(String code, String message) {
        return new ResultBean(code, message, null);
    }

    public static ResultBean error(BEErrors errors) {
        return new ResultBean(errors.getCode(), errors.getDesc(), null);
    }
}
