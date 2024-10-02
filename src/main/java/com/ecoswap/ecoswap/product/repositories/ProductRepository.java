package com.ecoswap.ecoswap.product.repositories;

import java.util.List;

import com.ecoswap.ecoswap.user.models.dto.UserDTO;
import com.ecoswap.ecoswap.user.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByCategory(String category);
    List<Product> findByUser(User user);
}
