package com.ecoswap.ecoswap.exchange.repositories;

import com.ecoswap.ecoswap.exchange.models.dto.ExchangeDTO;
import com.ecoswap.ecoswap.exchange.models.entities.Exchange;
import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findByProductTo(Product product);
    List<Exchange> findByStatus(String status);

    @Query("SELECT COUNT(e) FROM Exchange e WHERE e.productTo.id = :productId")
    int countInteractions(@Param("productId") Long productId);

    @Query("SELECT COUNT(e) FROM Exchange e WHERE e.productFrom.user.id = :userId AND e.status = 'completed'")
    int countSuccessfulExchanges(@Param("userId") Long userId);

    Long countByProductTo(Product productTo);

    @Query("SELECT COUNT(e) FROM Exchange e WHERE e.productTo.user.id = :userId AND e.status = 'completado'")
    Long countByProductTo_User(@Param("userId") Long userId);

}
