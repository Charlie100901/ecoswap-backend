package com.app.ecoswap.services;

import com.app.ecoswap.exceptions.ProductNotFoundException;
import com.app.ecoswap.models.Product;
import com.app.ecoswap.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(() -> new ProductNotFoundException("No existe el producto con id "+ id));
    }

    public Product createProduct(Product productRequest){
        try {
            Product product = iProductRepository.save(productRequest);
            return product;
        }catch (Exception e){
            throw new ProductNotFoundException("Error al crear el producto: "+ e.getMessage());
        }
    }

    public Product updateProductById(Long id, Product productRequest){
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
            return iProductRepository.save(productExisting);
        }else {
            throw new ProductNotFoundException("No se encontró un producto con el ID: "+ id);
        }

    }

    public String deleteProduct(Long id){
        try {
            iProductRepository.deleteById(id);
            return "Producto eliminado exitosamente";
        }catch (Exception e){
            throw new ProductNotFoundException("No se encontró un producto con el ID: "+ id);
        }
    }


}
