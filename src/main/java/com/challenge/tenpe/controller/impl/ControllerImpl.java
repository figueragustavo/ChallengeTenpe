package com.challenge.tenpe.controller.impl;

import com.challenge.tenpe.controller.Controller;
import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.service.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ControllerImpl implements Controller {

    private final Service service;
    
    private static AtomicInteger count =  new AtomicInteger(0);

    @Autowired
    public ControllerImpl(Service service) {
        this.service = service;
    }

    @Override
    @PostMapping("/sumaPorcentual")
    public ResponseEntity<ResponseDto> sumaPorcentual(@RequestBody RequestDto request) {
    	var numberProcess = String.format("%05d", count.incrementAndGet());
    	log.info("Inicio de proceso suma: #"+numberProcess);
    	Transaction tr = new Transaction();
    	tr.setStartDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
    	tr.setEndpoint("/sumaPorcentual");
    	tr.setRequest(request);
    	tr = service.sumaPorcentual(tr);
  
        return new ResponseEntity<>((ResponseDto) tr.getResponse(), HttpStatus.OK);
    }

	@Override
	@GetMapping("/pagingTransactions")
	public ResponseEntity<List<Transaction>> getTransacions(@RequestBody RequestTrDto request) {
		var numberProcess = String.format("%05d", count.incrementAndGet());
    	log.info("Inicio de proceso suma: #"+numberProcess);
    	Transaction tr = new Transaction();
    	tr.setStartDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
    	tr.setEndpoint("/pagingTransactions");
    	tr.setRequest(request);
		var trs = service.getTransactions(tr);
		return new ResponseEntity<>(trs, HttpStatus.OK);
	}
}
