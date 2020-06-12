package com.app.billsplitter.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "roommate")
@ToString
public class Roommate {

    @Id
    public String id;
    public String name;
    public String gender;
    public String phone;
}