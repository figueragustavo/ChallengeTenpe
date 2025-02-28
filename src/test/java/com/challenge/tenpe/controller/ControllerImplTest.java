package com.challenge.tenpo.controller;

import com.challenge.tenpe.controller.impl.ControllerImpl;
import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;
import com.challenge.tenpe.service.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@ExtendWith(MockitoExtension.class)
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
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransacions_ReturnsOkResponse() throws Exception {
        RequestTrDto request = RequestTrDto.builder().build();
        Transaction tr = new Transaction();
        tr.setDescriptionError("OK");
        List<Transaction> transactions = List.of(tr);

        when(service.getTransactions(any(Transaction.class))).thenReturn(transactions);

        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descriptionError").value(transactions.get(0).getDescriptionError()));
    }

    @Test
    void getTransacions_InvalidRequest_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sumaPorcentual_ValidRequest_ReturnsCorrectResponse() throws Exception {
        RequestDto request = new RequestDto();
        request.setNumber1(20);
        request.setNumber2(20);
        ResponseDto response = ResponseDto.builder().result(44.0).build();
        Transaction transaction = new Transaction();
        transaction.setResponse(response);

        when(service.sumaPorcentual(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/sumaPorcentual")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(44.0));
    }

    @Test
    void getTransacions_ValidRequest_ReturnsCorrectResponse() throws Exception {
        RequestTrDto request = RequestTrDto.builder().build();
        Transaction transaction = new Transaction();
        transaction.setDescriptionError("expectedValue");
        List<Transaction> transactions = List.of(transaction);

        when(service.getTransactions(any(Transaction.class))).thenReturn(transactions);

        mockMvc.perform(get("/pagingTransactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].someField").value("expectedValue"));
    }
}

