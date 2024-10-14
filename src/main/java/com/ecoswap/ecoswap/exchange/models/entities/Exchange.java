package com.ecoswap.ecoswap.exchange.models.entities;

import com.ecoswap.ecoswap.product.models.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_product_from", nullable = false)
    private Product productFrom;

    @ManyToOne
    @JoinColumn(name = "id_product_to", nullable = false)
    private Product productTo;

    @Column(nullable = true, length = 15)
    private String status;

    @Column
    private LocalDateTime exchangeRequestedAt;

    @Column
    private LocalDateTime exchangeRespondedAt;
}
