package com.example.microservices.core.product;

import com.example.api.core.product.Product;
import com.example.api.core.product.ProductService;
import com.example.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil  serviceUtil;

    public ProductServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Product getProduct(@PathVariable("productId") Long productId) {
        logger.debug("getProduct called - productId={}", productId);

        return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
    }
}
