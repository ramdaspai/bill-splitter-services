package com.app.billsplitter.controller;

import com.app.billsplitter.entity.Expense;
import com.app.billsplitter.entity.Payment;
import com.app.billsplitter.service.TransactionService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
public class TransactionController {

    private final Logger log = LoggerFactory.getLogger(TransactionController.class);

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(path = "/api/expenses", produces = APPLICATION_JSON_VALUE)
    public List<Expense> getExpenses() {
        return transactionService.getExpenses();
    }

    @PostMapping(path = "/api/expense/creation", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void addExpense(@RequestBody Expense expense) {
        log.info("Expense is: " + expense);
        transactionService.addTransaction(expense);
    }

    @GetMapping(path = "/api/expenses/{transactedBy}", produces = APPLICATION_JSON_VALUE)
    public List<Expense> getUserExpenses(@PathVariable("transactedBy") ObjectId transactedBy) {
        return transactionService.getUserExpenses(transactedBy);
    }

    @GetMapping(path = "/api/expenses/receivables/{transactedBy}", produces = APPLICATION_JSON_VALUE)
    public List<Payment> getUserReceivables(@PathVariable("transactedBy") ObjectId transactedBy) {
        return transactionService.getUserReceivables(transactedBy);
    }

    @GetMapping(path = "/api/expenses/payables/{transactedBy}", produces = APPLICATION_JSON_VALUE)
    public List<Payment> getUserPayables(@PathVariable("transactedBy") ObjectId transactedBy) {
        return transactionService.getUserPayables(transactedBy);
    }
}