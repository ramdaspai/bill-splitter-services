package com.app.billsplitter.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LineItem {

    private String id;
    private String name;
    private double amount;

}
