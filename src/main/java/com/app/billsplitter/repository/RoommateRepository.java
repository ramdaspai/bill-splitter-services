package com.app.billsplitter.repository;

import com.app.billsplitter.entity.Roommate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoommateRepository extends MongoRepository<Roommate, String> {
    Roommate findByPhone(String phoneNo);
}

