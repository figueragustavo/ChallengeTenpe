package com.challenge.tenpe.service;

import com.challenge.tenpe.dto.Transaction;

public interface Service {

    public Transaction sumaPorcentual(Transaction tr);
    
    public Transaction getTransactions(Transaction tr);
}
