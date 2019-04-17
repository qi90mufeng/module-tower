package com.albert.global;

import lombok.Data;

/**
 * 业务异常.
 */
@Data
public class BusinessException extends RuntimeException {

    private String code;
    private String desc;

    public BusinessException(BEErrors errors) {
        super("BusinessExcepiton -> code : " + errors.getCode() + ", desc : " + errors.getDesc());
        this.code = errors.getCode();
        this.desc = errors.getDesc();
    }

    public BusinessException(BEErrors errors, String message) {
        super("BusinessExcepiton -> code : " + errors.getCode() + ", desc : " + errors.getDesc() + " ," + message);
        this.code = errors.getCode();
        this.desc = errors.getDesc() + " ," + message;
    }

    public BusinessException(Throwable throwable) {
        super("BusinessExcepiton -> code : " + BEErrors.UNKNOW_ERROR.getCode() + ", desc : " + throwable.getMessage());
        this.code = BEErrors.UNKNOW_ERROR.getCode();
        this.desc = throwable.getMessage();
    }


}
