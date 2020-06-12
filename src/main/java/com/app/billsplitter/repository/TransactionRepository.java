package com.app.billsplitter.repository;

import com.app.billsplitter.entity.Expense;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Expense, String> {

    List<Expense> findByLineItems_id(ObjectId transactedBy);

}
