package com.albert.repository.primary;

import com.albert.document.primary.PrimaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrimaryRepository extends MongoRepository<PrimaryMongoObject, String> {
}
