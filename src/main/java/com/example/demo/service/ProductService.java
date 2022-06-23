package com.example.demo.service;

import com.example.demo.controller.dto.ProductRequestDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.transformer.ProductTransformer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductDTO;
import com.mongodb.MongoException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.http.HttpClient;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTransformer productTransformer;

    public ProductDTO create (ProductRequestDTO dto) {
        try {
            return productTransformer.toDTO(
                    productRepository.save(Product.builder()
                            .description(dto.getDescription())
                            .name(dto.getName())
                            .price(dto.getPrice())
                            .brand(dto.getBrand()).build()));

        } catch (DataIntegrityViolationException ex) {
            if (ex.getRootCause() instanceof SQLException) {
                final SQLException rootCause = (SQLException) ex.getRootCause();
                if (rootCause != null && rootCause.getSQLState().equals("23505")) {
                    throw new NotFoundException("Product already exists: " + dto.getId());
                } else {
                    throw ex;
                }
            } else {
                throw ex;
            }
        }
    }

    public ProductDTO update (ProductRequestDTO dto) {
        return productTransformer.toDTO(update(productRepository.findById(dto.getId()).orElseThrow(
                () -> new NotFoundException("Could not find product: " + dto.getId())), dto));
    }

    private Product update(Product product, ProductRequestDTO dto) {
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setBrand(dto.getBrand());
        return productRepository.save(product);
    }

    public ProductDTO findById(String id) {
        return productRepository.findById(id)
                .map(productTransformer::toDTO)
                .orElseThrow(() -> new NotFoundException("Could not find product: " + id));
    }

    public List<ProductDTO> findAll() {
        return  productRepository.findAll().stream().map(productTransformer::toDTO).collect(Collectors.toList());
    }

    public Set<ProductDTO> findAllOrdered() {
        return  productRepository.findAll().stream().sorted().map(productTransformer::toDTO).collect(Collectors.toSet());
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    public List<ProductDTO> findByName(String name) {

        var list = productRepository.findByName(name).stream()
                .map(productTransformer::toDTO).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        throw new NotFoundException("Could not find product: " + name);
    }

}
