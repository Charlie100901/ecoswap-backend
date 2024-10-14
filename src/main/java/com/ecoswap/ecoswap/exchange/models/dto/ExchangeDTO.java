package com.ecoswap.ecoswap.exchange.models.dto;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeDTO {
    private Long id;
    private Product productFrom;
    private Product productTo;
    private String status;
    private LocalDateTime exchangeRequestedAt;
    private LocalDateTime exchangeRespondedAt;
}
