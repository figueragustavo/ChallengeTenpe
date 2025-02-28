package com.challenge.tenpe.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.exception.PercentException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {
	
	 @ExceptionHandler(Exception.class)
	 public ResponseEntity<Transaction> handleError(Exception ex) {
		 ex.printStackTrace();
		 var tr = new Transaction();
		 tr.setStartDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
		 tr.setEndDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
		 tr.setDescriptionError(ex.getMessage());
		 log.error(ex.getMessage());
		 return new ResponseEntity<>(tr, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	 
	 @ExceptionHandler(PercentException.class)
	  public ResponseEntity<Transaction> handlePercentException(PercentException ex) {
		 var tr = ex.getTr();
		 tr.setDescriptionError(ex.getMsg());
     	 tr.setEndDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
		 return new ResponseEntity<>(tr, HttpStatus.NOT_FOUND);
	  }

}
