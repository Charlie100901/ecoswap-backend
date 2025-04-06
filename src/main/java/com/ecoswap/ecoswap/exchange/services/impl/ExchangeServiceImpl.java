package com.ecoswap.ecoswap.exchange.services.impl;

import com.ecoswap.ecoswap.exchange.exceptions.ExchangeNotFoundException;
import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.models.entities.Exchange;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.exchange.services.ExchangeService;
import com.ecoswap.ecoswap.notification.services.NotificationService;
import com.ecoswap.ecoswap.prediction.services.WekaPredictionService;
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

    @Autowired
    private final WekaPredictionService wekaPredictionService;

    @Autowired
    public ExchangeServiceImpl(WekaPredictionService wekaPredictionService) {
        this.wekaPredictionService = wekaPredictionService;
    }

    @Override
    public ExchangeDTO createRequestExchange(ExchangeDTO requestExchange) {
        // Cargar los productos completos desde la base de datos
        Product productFrom = productRepository.findById(requestExchange.getProductFrom().getId())
        .orElseThrow(() -> new IllegalStateException("El producto origen no existe."));
        Product productTo = productRepository.findById(requestExchange.getProductTo().getId())
            .orElseThrow(() -> new IllegalStateException("El producto destino no existe."));

        // Calcular los días publicados
        int daysPublished = productFrom.getReleaseDate() != null ? 
        (int) java.time.temporal.ChronoUnit.DAYS.between(productFrom.getReleaseDate(), LocalDateTime.now()) : 0;

        // Obtener interacciones y éxitos del usuario
        Long interactions = exchangeRepository.countByProductTo(productTo);
        Long userSuccessHistory = exchangeRepository.countByProductTo_User(productTo.getUser().getId());

        // Supongamos valores predeterminados para otros parámetros
        double userRating = 4.5; // Rating predeterminado
        String location = "cartagena"; // Ubicación predeterminada

        String userRatingCategory;
        if (userRating >= 4.5) {
            userRatingCategory = "bueno";
        } else if (userRating >= 3.0) {
            userRatingCategory = "regular";
        } else {
            userRatingCategory = "malo";
        }

        // Realizar la predicción
        boolean successPrediction = wekaPredictionService.predictExchangeSuccess(
        productFrom.getId(), daysPublished, interactions, userSuccessHistory, userRatingCategory, location
        );

        // Si la predicción es negativa, rechazar la solicitud
        if (!successPrediction) {
            System.out.println("La predicción indica que este intercambio no será exitoso.");
        }else {
            System.out.println("La predicción indica que el intercambio será exitoso");
        }


        Exchange exchange = new Exchange();
        exchange.setStatus("pendiente");
        exchange.setExchangeRequestedAt(LocalDateTime.now());
        exchange.setExchangeRespondedAt(LocalDateTime.now());
        exchange.setProductTo(requestExchange.getProductTo());
        exchange.setProductFrom(requestExchange.getProductFrom());

        exchangeRepository.save(exchange);

        Long productToId = requestExchange.getProductTo().getId();
        Optional<Product> productTo2 = productRepository.findById(productToId);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(productTo2.get().getUser().getId());
        userDTO.setName(productTo2.get().getUser().getName());
        userDTO.setEmail(productTo2.get().getUser().getEmail());
        userDTO.setAddress(productTo2.get().getUser().getAddress());
        userDTO.setCellphoneNumber(productTo2.get().getUser().getCellphoneNumber());

        notificationService.sendNotification(userDTO, "Tienes una nueva solicitud de intercambio para tu producto: " + 
        productTo2.get().getTitle());

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
