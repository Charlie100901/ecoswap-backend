package com.ecoswap.ecoswap.exchange.services.impl;

import com.ecoswap.ecoswap.exchange.exceptions.ExchangeNotFoundException;
import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.models.entities.Exchange;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.exchange.services.ExchangeService;
import com.ecoswap.ecoswap.notification.services.NotificationService;
import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;
import com.ecoswap.ecoswap.product.repositories.ProductRepository;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public ExchangeDTO createRequestExchange(ExchangeDTO requestExchange) {
        Exchange exchange = new Exchange();
        exchange.setStatus("pendiente");
        exchange.setExchangeRequestedAt(LocalDateTime.now());
        exchange.setExchangeRespondedAt(LocalDateTime.now());
        exchange.setProductTo(requestExchange.getProductTo());
        exchange.setProductFrom(requestExchange.getProductFrom());

        exchangeRepository.save(exchange);

        Long productToId = requestExchange.getProductTo().getId();
        Optional<Product> productTo = productRepository.findById(productToId);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(productTo.get().getUser().getId());
        userDTO.setName(productTo.get().getUser().getName());
        userDTO.setEmail(productTo.get().getUser().getEmail());
        userDTO.setAddress(productTo.get().getUser().getAddress());
        userDTO.setCellphoneNumber(productTo.get().getUser().getCellphoneNumber());

        notificationService.sendNotification(userDTO, "Tienes una nueva solicitud de intercambio para tu producto: " + 
                        productTo.get().getTitle());

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

    @Override
    public List<ExchangeDTO> findByProductTo(ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(productDTO.getId());
        List<Exchange> exchanges = exchangeRepository.findByProductTo(product.get());

        return exchanges.stream()
                .map(exchange -> new ExchangeDTO(
                        exchange.getId(),
                        exchange.getProductFrom(),
                        exchange.getProductTo(),
                        exchange.getStatus(),
                        exchange.getExchangeRequestedAt(),
                        exchange.getExchangeRespondedAt()
                ))
                .collect(Collectors.toList());

    }

    @Override
    public Long countExchanges() {
        return exchangeRepository.count();
    }
}
