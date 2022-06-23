package com.example.demo.controller;


import com.example.demo.controller.dto.ProductRequestDTO;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.ProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "${api.version}/products", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@EnableSwagger2
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        ProductDTO response = this.productService.create(dto);
        return ResponseEntity.created(getLocation("/products", response.getId())).body(response);
    }

    @PutMapping
    public ResponseEntity<ProductDTO> updateProducts(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(this.productService.update(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(this.productService.findById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(this.productService.findByName(name));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(required = true) String id) {
        this.productService.deleteById(id);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @GetMapping("/ordered")
    public ResponseEntity<Set<ProductDTO>> getAllOrdered() {
        return ResponseEntity.ok(this.productService.findAllOrdered());
    }

    public URI getLocation(String path, String id) {
        return URI.create(String.format("v1".concat(path).concat("/%s"), id));
    }

}
