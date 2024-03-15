package com.app.ecoswap.services;

import com.app.ecoswap.exceptions.NotProductFoundException;
import com.app.ecoswap.models.Product;
import com.app.ecoswap.repositories.IProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private IProductRepository iProductRepository;

    public List<Product> getAllProducts(){
        return iProductRepository.findAll();
    }

    public Product getProductById(Long id){
        return iProductRepository.findById(id)
                .orElseThrow(() -> new NotProductFoundException("No existe el producto con id "+ id));
    }

    public ResponseEntity<String> createProduct(Product productRequest){
        try {
            Product product = iProductRepository.save(productRequest);
            return ResponseEntity.ok("Producto creado exitosamente con id: "+ product.getId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<Product> updateProductById(Long id, Product productRequest){
        Optional<Product> product = iProductRepository.findById(id);
        if(product.isPresent()){
            Product productExisting = product.get();
            productExisting.setTitle(productRequest.getTitle());
            productExisting.setUser(productExisting.getUser());
            productExisting.setCategory(productRequest.getCategory());
            productExisting.setDescription(productRequest.getDescription());
            productExisting.setProductStatus(productRequest.getProductStatus());
            productExisting.setImageProduct(productRequest.getImageProduct());
            productExisting.setUser(productExisting.getUser());
            Product updatedProduct = iProductRepository.save(productExisting);
            return ResponseEntity.ok(updatedProduct);
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<String> deleteProduct(Long id){
        try {
            iProductRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Producto eliminado exitosamente");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Producto no pudo ser eliminado");
        }
    }


}
