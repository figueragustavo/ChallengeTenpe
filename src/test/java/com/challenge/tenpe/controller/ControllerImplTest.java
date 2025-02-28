package com.challenge.tenpe.controller;

import com.challenge.tenpe.controller.impl.ControllerImpl;
import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.service.Service;
import com.challenge.tenpe.util.Util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ControllerImpl.class)
public class ControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Service service;

    @Test
    void sumaPorcentual_ReturnsOkResponse() throws Exception {
        RequestDto request = new RequestDto();
        ResponseDto response = ResponseDto.builder().result(44.0).build();
        Transaction transaction = new Transaction();
        transaction.setResponse(response);

        when(service.sumaPorcentual(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/sumaPorcentual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(response.getResult()));
    }

    @Test
    void sumaPorcentual_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/sumaPorcentual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransacions_ReturnsOkResponse() throws Exception {
        RequestTrDto request = RequestTrDto.builder().build();
        Transaction tr = new Transaction();
        tr.setDescriptionError("OK");
        List<Transaction> transactions = List.of(tr);
        tr.setResponse(transactions);

        when(service.getTransactions(any(Transaction.class))).thenReturn(tr);

        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getTransacions_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sumaPorcentual_ValidRequest_ReturnsCorrectResponse() throws Exception {
        RequestDto request = new RequestDto();
        request.setNumber1(20.0);
        request.setNumber2(20.0);
        ResponseDto response = ResponseDto.builder().result(44.0).build();
        Transaction transaction = new Transaction();
        transaction.setStartDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
        transaction.setEndpoint("/sumaPorcentual");
        transaction.setRequest(request);
        transaction.setResponse(response);
        transaction.setDescriptionError("OK");
        transaction.setEndDate(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

        when(service.sumaPorcentual(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/sumaPorcentual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.toJson(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(44.0));
    }

    @Test
    void getTransacions_ValidRequest_ReturnsCorrectResponse() throws Exception {
        RequestTrDto request = RequestTrDto.builder().build();
        Transaction transaction = new Transaction();
        transaction.setDescriptionError("OK");
        List<Transaction> transactions = List.of(transaction);
        transaction.setResponse(transactions);

        when(service.getTransactions(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}

