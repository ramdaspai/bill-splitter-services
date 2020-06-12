package com.app.billsplitter.entity;

import lombok.Data;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "expense")
@Data
@ToString
public class Expense {

    @Id
    private String id;
    private String category;
    private String details;
    private Date date;
    private double totalAmount;
    private ObjectId transactionBy;
    private List<LineItem> lineItems;

    public String getTransactionBy() {
        return transactionBy.toHexString();
    }
}
