package com.ecoswap.ecoswap.product.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.ecoswap.ecoswap.product.exceptions.ProductCreationException;
import com.ecoswap.ecoswap.user.models.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
            null,
            LocalDate.now()
        ))
        .collect(Collectors.toList());
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User usuarioAutenticado = (User) auth.getPrincipal();


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
            product.setTitle(productDTO.getTitle());
            product.setDescription(productDTO.getDescription());
            product.setCategory(productDTO.getCategory());
            product.setImageProduct("ssfgsa");
            product.setConditionProduct(productDTO.getConditionProduct());
            product.setUser(usuarioAutenticado);
            product.setProductStatus("activo");
            product.setReleaseDate(LocalDate.now());

            // Guardar el producto y convertirlo a DTO
            Product savedProduct = productRepository.save(product);
            ProductDTO productDTOResponse = new ProductDTO();
            productDTOResponse.setId(savedProduct.getId());
            productDTOResponse.setDescription(savedProduct.getDescription());
            productDTOResponse.setCategory(savedProduct.getCategory());
            productDTOResponse.setConditionProduct(savedProduct.getConditionProduct());
            productDTOResponse.setTitle(savedProduct.getTitle());
            productDTOResponse.setImageProduct(savedProduct.getImageProduct());
//            productDTOResponse.setUser(savedProduct.getUser().getEmail()); // o el atributo que desees
            productDTOResponse.setReleaseDate(savedProduct.getReleaseDate());


            return productDTOResponse;

        } catch (Exception e) { //CAMBIAR LA EXCEPCION POR IOEXCEPTION cuando suba la imagen
            throw new ProductCreationException("Error al crear el producto: " + e.getMessage());
        }
    }

    @Override
    public ProductDTO updateProductById(Long id, ProductDTO productDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioAutenticado = (User) auth.getPrincipal();

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("El producto no existe"));
        if(product.getUser().getId() != usuarioAutenticado.getId()){
            throw new RuntimeException("No tienes permiso para esta acción");
        }

        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setImageProduct(productDTO.getImageProduct());
        product.setConditionProduct(productDTO.getConditionProduct());

        Product savedProduct = productRepository.save(product);

        ProductDTO productDTOResponse = new ProductDTO();
        productDTOResponse.setId(savedProduct.getId());
        productDTOResponse.setDescription(savedProduct.getDescription());
        productDTOResponse.setCategory(savedProduct.getCategory());
        productDTOResponse.setConditionProduct(savedProduct.getConditionProduct());
        productDTOResponse.setTitle(savedProduct.getTitle());
        productDTOResponse.setImageProduct(savedProduct.getImageProduct());
        productDTOResponse.setReleaseDate(savedProduct.getReleaseDate());

        return productDTOResponse;
    }

    @Override
    public void deleteProduct(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioAutenticado = (User) auth.getPrincipal();

        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("El producto no existe"));
        if(product.getUser().getId() != usuarioAutenticado.getId()){
            throw new RuntimeException("No tienes permiso para esta acción");
        }

        product.setProductStatus("inactivo");

        productRepository.save(product);

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
                null,
                LocalDate.now()
        );
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);

        List<ProductDTO> productDTOs = products.stream()
                .filter(product -> "activo".equals(product.getProductStatus()))
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setTitle(product.getTitle());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setCategory(product.getCategory());
                    productDTO.setConditionProduct(product.getConditionProduct());
                    productDTO.setImageProduct(product.getImageProduct());
                    productDTO.setReleaseDate(product.getReleaseDate());
                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOs;

    }

    @Override
    public List<ProductDTO> getProductsByUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuarioAutenticado = (User) auth.getPrincipal();

        List<Product> products = productRepository.findByUser(usuarioAutenticado);

        List<ProductDTO> productDTOs = products.stream()
                .filter(product -> "activo".equals(product.getProductStatus()))
                .map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(product.getId());
                    productDTO.setTitle(product.getTitle());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setCategory(product.getCategory());
                    productDTO.setConditionProduct(product.getConditionProduct());
                    productDTO.setImageProduct(product.getImageProduct());
                    productDTO.setReleaseDate(product.getReleaseDate());
                    return productDTO;
                })
                .collect(Collectors.toList());

        return productDTOs;

    }

}
