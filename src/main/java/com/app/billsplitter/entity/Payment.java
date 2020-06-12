package com.app.billsplitter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Payment {

    @NonNull
    private Roommate from;
    @NonNull
    private Roommate to;
    @NonNull
    private double amount;
    private List<String> transactions = new ArrayList<>();
}
