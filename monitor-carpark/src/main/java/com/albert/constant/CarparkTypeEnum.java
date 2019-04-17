package com.albert.constant;

import lombok.Getter;

/**
 * 车位类型,仅供参考
 */
@Getter
public enum CarparkTypeEnum {

    TRUCK_CONTRACT("001","卡车合约车位"),
    TRUCK_TEMP("002","卡车临时车位"),
    CAR_CONTRACT("003","小车合约车位"),
    CAR_TEMP("005","小车临时车位"),
    ;

    private String code;  //停车位类型编码
    private String desc;  //停车位类型描述

    CarparkTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
