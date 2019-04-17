package com.albert.document.dto.carpark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkBaseInfoRspDTO {

    /**
     * id
     */
    private String id;

    /**
     * 停车场编号
     */
    private String parkNum;

    /**
     * 停车场名称
     */
    private String parkName;

    /**
     * 停车场地址
     */
    private String address;

    /**
     * 车位总数量
     */
    private Long totalNum;

    /**
     * 车位信息 json串
     * truck : 卡车车位   car: 小车车位
     * contract: 合约车位  temp:  临时车位
     * count: 数量   hourAmt: 10元/小时  dailyMax: 120元   monthAmt:200元
     * {"truck_contract":{"count":0, "monthAmt":0},
     * "truck_temp":{"count":20, "hourAmt":10, "dailyMax":120},
     * "car_contract":{"count":30, "monthAmt":150},
     * "car_temp":{"count":100, "hourAmt":5, "dailyMax":60}}
     */
    private String parksInfo;

    /**
     * 状态  1、启用   0、停用
     * 其他状态暂不考虑  2、仅对合约车开放
     */
    private Integer status;
}