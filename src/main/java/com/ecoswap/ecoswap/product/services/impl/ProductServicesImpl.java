package com.ecoswap.ecoswap.product.services.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.ecoswap.ecoswap.product.exceptions.ProductCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoswap.ecoswap.product.exceptions.ProductNotFoundException;
import com.ecoswap.ecoswap.product.models.dto.ProductDTO;
import com.ecoswap.ecoswap.product.models.entities.Product;
import com.ecoswap.ecoswap.product.repositories.ProductRepository;
import com.ecoswap.ecoswap.product.services.ProductService;
import com.ecoswap.ecoswap.user.models.dto.UserDTO;

@Service
public class ProductServicesImpl implements ProductService{

    @Autowired
    public ProductRepository productRepository;

  
    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
        .filter(product -> "activo".equals(product.getProductStatus()))
        .map(product -> new ProductDTO(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getCategory(),
            product.getConditionProduct(),
            product.getImageProduct(),
            null,
            LocalDate.now()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {

//            String emailUser = sessionTokenService.getUserEmailFromToken(token);
//            User user = userRepository.findUserByEmail(emailUser)
//                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

//            if (image == null || image.isEmpty()) {
//                throw new IllegalArgumentException("Debe proporcionar una imagen para crear el producto");
//            }

            // Obtener el nombre de la imagen
//            String imageName = image.getOriginalFilename();
//            // Obtener la extensión del archivo
//            String fileExtension = imageName.substring(imageName.lastIndexOf(".") + 1).toLowerCase();

            // Verificar si la extensión es válida
//            if (!Arrays.asList("jpg", "jpeg", "png").contains(fileExtension)) {
//                throw new FileFormatException("Extensión de imagen no permitida. Por favor suba una imagen con extensión jpg, png o jpeg");
//            }

            // Crear un nombre único para cada imagen
//            String uniqueFileName = UUID.randomUUID().toString() + "_" + imageName;
//            // Ruta donde se guardarán las imágenes en el servidor
//            String serverImagePath = "http://localhost:8080/images/" + uniqueFileName;
//
//            // Guardar la imagen en el servidor
//            Files.write(Paths.get(storageFolderPath, uniqueFileName), image.getBytes());

            // Guardar la URL del servidor en el objeto Product
//            productRequest.setImageProduct(serverImagePath);
//            productDTO.setUser(user);
//            productDTO.setProductStatus("activo");
//            productDTO.setReleaseDate(LocalDate.now());

            //Pasar el DTO a entidad para guardarlo
            Product product = new Product();
            product.setId(productDTO.getId());
            product.setImageProduct("ssfgsa");
            product.setConditionProduct(productDTO.getConditionProduct());
            product.setUser(null);
            product.setProductStatus("activo");
            product.setReleaseDate(LocalDate.now());

            // Guardar el producto y convertirlo a DTO
            Product savedProduct = productRepository.save(product);
            ProductDTO productDTOResponse = new ProductDTO();
            productDTO.setId(savedProduct.getId());
            productDTO.setTitle(savedProduct.getTitle());
            productDTO.setImageProduct(savedProduct.getImageProduct());
//            productDTO.setUser(savedProduct.getUser().getEmail()); // o el atributo que desees
            productDTO.setProductStatus(savedProduct.getProductStatus());
            productDTO.setReleaseDate(savedProduct.getReleaseDate());


            return productDTOResponse;

        } catch (Exception e) { //CAMBIAR LA EXCEPCION POR IOEXCEPTION
            throw new ProductCreationException("Error al crear el producto: " + e.getMessage());
        }
    }

    @Override
    public ProductDTO updateProductById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductById'");
    }

    @Override
    public void deleteProduct(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("No existe el producto con id " + id));
    
        return new ProductDTO(
            product.getId(),
            product.getTitle(),
            product.getDescription(),
            product.getCategory(),
            product.getConditionProduct(),
            product.getImageProduct(),
            null,
            product.getReleaseDate()
        );
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByCategory'");
    }

    @Override
    public List<ProductDTO> getProductsByUser(UserDTO user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProductsByUser'");
    }

}
