package com.example.microservices.core.product.services;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.api.exceptions.InvalidInputException;
import com.example.api.exceptions.NotFoundException;
import com.example.microservices.core.product.persistence.ProductEntity;
import com.example.microservices.core.product.persistence.ProductRepository;
import com.example.util.http.ServiceUtil;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

// Business login (calling repository)
@RestController
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ServiceUtil serviceUtil;
    private final ProductMapper mapper;

    public ProductServiceImpl(ProductRepository productRepository, ServiceUtil serviceUtil, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.serviceUtil = serviceUtil;
        this.mapper = mapper;
    }

    @Override
    public Product getProduct(Long id) {
        if(id < 1) throw new InvalidInputException("Product id must be greater than zero");

        ProductEntity entity = productRepository.findByProductId(id)
                .orElseThrow(() -> new NotFoundException("Product id " + id + " not found"));

        Product response = mapper.entityToApi(entity);
        response.setServiceAddress(serviceUtil.getServiceAddress());

        return response;
    }

    @Override
    public Product createProduct(Product product) {
        try {
            ProductEntity entity = mapper.apiToEntity(product);
            ProductEntity newEntity = productRepository.save(entity);
            return mapper.entityToApi(newEntity);
        } catch (DuplicateKeyException ex){
            throw new InvalidInputException("Duplicate key, Product Id: " + product.getProductId());
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.findByProductId(productId).ifPresent(productRepository::delete);
    }
}
