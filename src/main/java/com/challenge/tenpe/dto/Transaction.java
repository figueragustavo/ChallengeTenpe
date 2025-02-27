package com.challenge.tenpe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.challenge.tenpe.entity.TransactionEntity;

@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    private Date startDate;
    private Date endDate;
    private String endpoint;
    private Object request;
    private Object response;
    private String descriptionError;
    
    public Transaction(TransactionEntity entity) {
    	this.startDate = entity.getStartDate();
    	this.endDate = entity.getEndDate();
    	this.endpoint = entity.getEndpoint();
    	this.request = entity.getRequest();
    	this.response = entity.getResponse();
    	this.descriptionError = entity.getDescriptionError();
    }
}
