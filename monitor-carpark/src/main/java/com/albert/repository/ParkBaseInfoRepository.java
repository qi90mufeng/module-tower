package com.albert.repository;

import com.albert.document.carpark.ParkBaseInfoPo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkBaseInfoRepository extends MongoRepository<ParkBaseInfoPo, String> {
}
