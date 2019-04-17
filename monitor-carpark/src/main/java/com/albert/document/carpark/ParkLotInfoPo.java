package com.albert.document.carpark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 停车场车位信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "park_lot_info")
public class ParkLotInfoPo {

    @Id
    private String id;

    /**
     * 停车场编号
     */
    private String parkNum;

    /**
     * 车位编号
     */
    private String lotNum;

    /**
     * 车位类型
     */
    private String lotType;

    /**
     * 状态   0、停用    1、启用空闲    2、启用使用中
     */
    private Integer status;
}