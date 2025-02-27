package com.challenge.tenpe.controller;

import com.challenge.tenpe.dto.RequestDto;
import com.challenge.tenpe.dto.RequestTrDto;
import com.challenge.tenpe.dto.ResponseDto;
import com.challenge.tenpe.dto.Transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface Controller {
	
    @Operation(summary = "Suma porcentual", description = "Suma porcentual de dos números")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Suma porcentual exitosa"),
            @ApiResponse(responseCode = "400", description = "Petición inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno")
    })
    public  ResponseEntity<ResponseDto> sumaPorcentual(@RequestBody RequestDto request);
    
    public ResponseEntity<List<Transaction>> getTransacions(@RequestBody RequestTrDto request);

}
