package com.ecoswap.ecoswap.exchange.services;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.product.models.dto.ProductDTO;

import java.util.List;

public interface ExchangeService {
    ExchangeDTO createRequestExchange(ExchangeDTO requestExchange);
    ExchangeDTO selectExchangeRequest(ExchangeDTO requestExchange);
    List<ExchangeDTO> findByProductTo(ProductDTO productDTO);
    Long countExchanges();
}
