package com.ecoswap.ecoswap.prediction.controllers;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.repositories.ExchangeRepository;
import com.ecoswap.ecoswap.prediction.models.dto.ManualPredictionDTO;
import com.ecoswap.ecoswap.prediction.models.dto.PredictionResult;
import com.ecoswap.ecoswap.prediction.services.WekaPredictionService;
import com.ecoswap.ecoswap.product.models.entities.Product;
import com.ecoswap.ecoswap.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PredictionController {

    @Autowired
    private WekaPredictionService wekaPredictionService;

    @PostMapping("/prediction")
    public ResponseEntity<Map<String, Object>> makePrediction(@RequestBody ExchangeDTO requestExchange) {
        Map<String, Object> response = wekaPredictionService.generatePredictionResponse(requestExchange);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/prediction-manual")
    public ResponseEntity<Map<String, Object>> predictManual(@RequestBody ManualPredictionDTO manualDTO) {
        Map<String, Object> response = wekaPredictionService.generateManualPrediction(manualDTO);
        return ResponseEntity.ok(response);
    }

}
