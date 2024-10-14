package com.ecoswap.ecoswap.exchange.services.impl;

import com.ecoswap.ecoswap.exchange.exceptions.ExchangeNotFoundException;
import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.models.entities.Exchange;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.exchange.services.ExchangeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Override
    public ExchangeDTO createRequestExchange(ExchangeDTO requestExchange) {
        Exchange exchange = new Exchange();
        exchange.setStatus("pendiente");
        exchange.setExchangeRequestedAt(LocalDateTime.now());
        exchange.setExchangeRespondedAt(LocalDateTime.now());
        exchange.setProductTo(requestExchange.getProductTo());
        exchange.setProductFrom(requestExchange.getProductFrom());

        exchangeRepository.save(exchange);

        ExchangeDTO responseExchange = new ExchangeDTO();
        responseExchange.setId(exchange.getId());
        responseExchange.setStatus(exchange.getStatus());
        responseExchange.setExchangeRequestedAt(exchange.getExchangeRequestedAt());
        responseExchange.setExchangeRespondedAt(exchange.getExchangeRespondedAt());
        responseExchange.setProductTo(exchange.getProductTo());
        responseExchange.setProductFrom(exchange.getProductFrom());

        return responseExchange;
    }

    @Override
    @Transactional
    public ExchangeDTO selectExchangeRequest(ExchangeDTO requestExchange) {
        Exchange optionalExchange = exchangeRepository.findById(requestExchange.getId()).orElseThrow(() -> new ExchangeNotFoundException("No existe el intercambio"));

        optionalExchange.setStatus("completado");
        optionalExchange.setExchangeRespondedAt(LocalDateTime.now());

        List<Exchange> requestExchangeAsocidas = exchangeRepository.findByProductTo(requestExchange.getProductTo());
        for (Exchange rExchange: requestExchangeAsocidas){
            if (rExchange.getStatus().equals("pendiente")){
                rExchange.setStatus("rechazada");
            }
        }

        exchangeRepository.saveAll(requestExchangeAsocidas);

        ExchangeDTO completedExchangeDTO = new ExchangeDTO();
        completedExchangeDTO.setId(optionalExchange.getId());
        completedExchangeDTO.setStatus(optionalExchange.getStatus());
        completedExchangeDTO.setProductTo(optionalExchange.getProductTo());
        completedExchangeDTO.setProductFrom(optionalExchange.getProductFrom());
        completedExchangeDTO.setExchangeRequestedAt(optionalExchange.getExchangeRequestedAt());
        completedExchangeDTO.setExchangeRespondedAt(optionalExchange.getExchangeRespondedAt());

        return completedExchangeDTO;
    }
}
