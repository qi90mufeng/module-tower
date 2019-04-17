package com.albert.document.carpark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "park_lot_type")
public class ParkLotTypePo{

    @Id
    private String id;

    /**
     * 车位类型编码
     */
    private String code;

    /**
     * 车位类型名称
     */
    private String desc;

    @Override
    public String toString() {
        return "ParkLotTypePo{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}