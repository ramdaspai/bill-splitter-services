package com.app.billsplitter.service;

import com.app.billsplitter.entity.Expense;
import com.app.billsplitter.entity.Payment;
import com.app.billsplitter.entity.Roommate;
import com.app.billsplitter.repository.TransactionRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final Logger log = LoggerFactory.getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;
    private RoommateService roommateService;

    public TransactionService(TransactionRepository transactionRepository, RoommateService roommateService) {
        this.transactionRepository = transactionRepository;
        this.roommateService = roommateService;
    }

    public List<Expense> getExpenses() {
        return transactionRepository.findAll();
    }

    public void addTransaction(Expense expense) {
        transactionRepository.save(expense);
        log.info("Successfully saved the transaction in the DB: " + expense);
    }

    public List<Expense> getUserExpenses(ObjectId id) {
        List<Expense> result = transactionRepository.findByLineItems_id(id);
        log.info("result: " + result);
        return result;
    }

    public List<Payment> getUserReceivables(ObjectId transactedBy) {
        List<Expense> expenses = getUserExpenses(transactedBy);
        Map<String, Payment> userMap = getUserSettlement(expenses);
        List<Payment> settledPayments = new ArrayList<>(userMap.values());
        return settledPayments.stream().filter(p -> p.getTo().getId().equalsIgnoreCase(transactedBy.toString()))
                .collect(Collectors.toList());
    }

    public List<Payment> getUserPayables(ObjectId transactedBy) {
        List<Expense> expenses = getUserExpenses(transactedBy);
        Map<String, Payment> userMap = getUserSettlement(expenses);
        List<Payment> settledPayments = new ArrayList<>(userMap.values());
        return settledPayments.stream().filter(p -> p.getFrom().getId().equalsIgnoreCase(transactedBy.toString()))
                .collect(Collectors.toList());
    }

    private Map<String, Payment> getUserSettlement(List<Expense> expenses) {
        Map<String, Roommate> roommateMap = roommateService.getAllRoommates().stream().collect(
                Collectors.toMap(Roommate::getId, x -> x));
        Map<String, Payment> userMap = new HashMap<>();

        expenses.forEach(expense ->
            expense.getLineItems().forEach(lineItem -> {
                if (!lineItem.getId().equalsIgnoreCase(expense.getTransactionBy())) {
                    Payment payment = new Payment(roommateMap.get(lineItem.getId()),
                            roommateMap.get(expense.getTransactionBy()), lineItem.getAmount());
                    if(userMap.containsKey(lineItem.getId())) {
                        Payment currentPayment = userMap.get(lineItem.getId());
                        if (currentPayment.getFrom().getId().equalsIgnoreCase(payment.getFrom().getId())
                                && currentPayment.getTo().getId().equalsIgnoreCase(payment.getTo().getId())) {
                            double updatedAmount = currentPayment.getAmount() + lineItem.getAmount();
                            currentPayment.setAmount(updatedAmount);
                            currentPayment.getTransactions().add(expense.getId());
                            userMap.put(lineItem.getId(), currentPayment);
                        }
                    } else {
                        payment.getTransactions().add(expense.getId());
                        userMap.put(lineItem.getId(), payment);
                    }
                }
            })
        );
        return userMap;
    }
}
