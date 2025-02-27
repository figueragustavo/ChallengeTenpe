package com.challenge.tenpe.service.impl;

import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.entity.TransactionEntity;
import com.challenge.tenpe.feign.PorcentFeign;
import com.challenge.tenpe.repository.TenpeRepository;
import com.challenge.tenpe.util.Util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements com.challenge.tenpe.service.Service {

    @Autowired
    private TenpeRepository repository;
    
    @Autowired
    private PorcentFeign porcentFeign;

    @Autowired
    private CacheManager cacheManager;

    @Async
    public void saveTransactionAsync(TransactionEntity entity) {
        repository.save(entity);
    }

    @Override
    public List<Transaction> getTransactions(Transaction tr) {
    	RequestTrDto req = (RequestTrDto) tr.getRequest();
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        
        Page<TransactionEntity> pagedResult = repository.findAll(pageable);
        
        List<Transaction> transactions = new ArrayList();
		for(TransactionEntity transaction : (List<TransactionEntity>) pagedResult.getContent()) {
			transactions.add(new Transaction(transaction));
		}
        return transactions;
    }

    @Override
    public Transaction sumaPorcentual(Transaction tr) {
    	double result =  this.sumaConPorcentaje((RequestDto)tr.getRequest(), getPercent());
    	tr.setResponse(ResponseDto.builder().result(result).build());
    	tr.setDescriptionError("OK");
    	tr.setEndDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
    	saveTransactionAsync(mapToEntity(tr));
        return tr;
    }

    private double sumaConPorcentaje(RequestDto request, double porcentaje) {
        double suma = request.getNumber1() + request.getNumber2();
        double incremento = suma * (porcentaje / 100);
        return suma + incremento;
    }

    @Cacheable(value = "percentCache", key = "'getPercent'")
    private Double getPercent() {
        int intentos = 0;
        while (intentos < 3) {
            try {
                var porcentaje = porcentFeign.getPercent().getPorcentaje();
                return porcentaje;
            } catch (Exception e) {
                intentos++;
            }
        }
//        if (intentos == 3) {
            var cache = cacheManager.getCache("percentCache");
            if (cache != null && cache.get("getPercent") != null) {
                return (double) cache.get("getPercent").get();
            } else {
                throw new RuntimeException("No se pudo obtener el porcentaje y no hay valor en caché");
            }
//        }
    }
    
    public TransactionEntity mapToEntity(Transaction dto) {
        TransactionEntity entity = new TransactionEntity();
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setEndpoint(dto.getEndpoint());
        entity.setRequest(Util.toJson(dto.getRequest()) );
        entity.setResponse(Util.toJson(dto.getResponse()));
        entity.setDescriptionError(dto.getDescriptionError());
        return entity;
    }

}
