package com.ecoswap.ecoswap.exchange.services;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;

public interface ExchangeService {
    ExchangeDTO createRequestExchange(ExchangeDTO requestExchange);
    ExchangeDTO selectExchangeRequest(ExchangeDTO requestExchange);
}
