package com.ecoswap.ecoswap.exchange.controllers;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping("/create-exchange")
    public ResponseEntity<ExchangeDTO> createRequestExchange(@RequestBody ExchangeDTO exchangeDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(exchangeService.createRequestExchange(exchangeDTO));
    }

    @PostMapping("/select-exchange")
    public ResponseEntity<ExchangeDTO> selectExchangeRequest(@RequestBody ExchangeDTO exchangeDTO){
        return ResponseEntity.ok(exchangeService.selectExchangeRequest(exchangeDTO));
    }
}
