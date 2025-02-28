package com.challenge.tenpe.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.tenpe.dto.PercentDto;
import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.entity.TransactionEntity;
import com.challenge.tenpe.feign.PorcentFeign;
import com.challenge.tenpe.repository.TenpeRepository;
import com.challenge.tenpe.service.impl.ServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

class ServiceImplTest {

    @Mock
    private TenpeRepository repository;

    @Mock
    private PorcentFeign porcentFeign;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private ServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTransactions_returnsTransactions() {
        RequestTrDto requestTrDto = RequestTrDto.builder().page(0).size(10).build();
        Transaction tr = new Transaction();
        tr.setRequest(requestTrDto);

        TransactionEntity entity = new TransactionEntity();
        Page<TransactionEntity> pagedResult = new PageImpl<>(Collections.singletonList(entity));
        when(repository.findAll(any(Pageable.class))).thenReturn(pagedResult);

        Transaction result = service.getTransactions(tr);

        assertNotNull(result);
        assertEquals("OK", result.getDescriptionError());
        assertNotNull(result.getResponse());
    }

    @Test
    void sumaPorcentual_returnsCorrectSum() {
        RequestDto requestDto = new RequestDto();
        PercentDto percent = new PercentDto(10.0);
        requestDto.setNumber1(100);
        requestDto.setNumber2(50);
        Transaction tr = new Transaction();
        tr.setRequest(requestDto);

        when(porcentFeign.getPercent()).thenReturn(percent);

        Transaction result = service.sumaPorcentual(tr);

        assertNotNull(result);
        assertEquals("OK", result.getDescriptionError());
        ResponseDto response = (ResponseDto) result.getResponse();
        assertEquals(165.0, response.getResult());
    }

//    @Test
    void getPercent_returnsCachedValue_whenFeignFails() {
        when(porcentFeign.getPercent()).thenThrow(new RuntimeException("Feign error"));
        ConcurrentMapCache cache = new ConcurrentMapCache("percentCache");
        cache.put("getPercent", new SimpleValueWrapper(5.0));
        when(cacheManager.getCache("percentCache")).thenReturn(cache);

        Double percent = service.getPercent();

        assertEquals(5.0, percent);
    }

    @Test
    void getPercent_throwsException_whenFeignFailsAndNoCache() {
        when(porcentFeign.getPercent()).thenThrow(new RuntimeException("Feign error"));
        when(cacheManager.getCache("percentCache")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> service.getPercent());
    }
}
