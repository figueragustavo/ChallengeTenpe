package com.challenge.tenpe.service;

import java.util.List;

import com.challenge.tenpe.dto.Transaction;

public interface Service {

    public Transaction sumaPorcentual(Transaction tr);
    
    public List<Transaction> getTransactions(Transaction tr);
}
