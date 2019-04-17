package com.albert.document.carpark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "park_base")
public class ParkBaseInfoPo {

    @Id
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
     * 状态  1、启用   0、停用
     * 其他状态暂不考虑  2、仅对合约车开放
     */
    private Integer status;

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

    @Override
    public String toString() {
        return "ParkBaseInfoPo{" +
                "id='" + id + '\'' +
                ", parkNum='" + parkNum + '\'' +
                ", parkName='" + parkName + '\'' +
                ", address='" + address + '\'' +
                ", totalNum=" + totalNum +
                ", status=" + status +
                ", parksInfo='" + parksInfo + '\'' +
                '}';
    }
}