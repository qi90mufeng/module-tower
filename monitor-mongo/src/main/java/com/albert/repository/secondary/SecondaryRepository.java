package com.albert.repository.secondary;

import com.albert.document.secondary.SecondaryMongoObject;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SecondaryRepository extends MongoRepository<SecondaryMongoObject, String> {
}
