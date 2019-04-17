package com.albert.repository;

import com.albert.document.carpark.ParkLotTypePo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarparkTypeRepository extends MongoRepository<ParkLotTypePo, String> {
}
