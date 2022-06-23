package com.example.demo.entity.transformer;

import com.example.demo.entity.Product;
import com.example.demo.service.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductTransformer extends AbstractTransformer<Product, ProductDTO>{
    protected ProductTransformer() {
        super(Product.class, ProductDTO.class);
    }
}