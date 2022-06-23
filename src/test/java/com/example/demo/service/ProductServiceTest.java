package com.example.demo.service;


import com.example.demo.controller.dto.ProductRequestDTO;
import com.example.demo.entity.Product;
import com.example.demo.entity.transformer.ProductTransformer;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.ProductDTO;
import com.mongodb.MongoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.SQLException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest extends AbstractTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static ProductTransformer productTransformer;

    @BeforeAll
    static void setUp() {
        productTransformer = mock(ProductTransformer.class);
        ModelMapper modelMapper = new ModelMapper();
        ReflectionTestUtils.setField(productTransformer, "modelMapper", modelMapper);
        ReflectionTestUtils.setField(productTransformer, "dtoClass", ProductDTO.class);
    }

    @BeforeEach
    void beforeEach() {
        when(productTransformer.toDTO(any(Product.class))).thenCallRealMethod();
    }

    @Test
    void updateWhithInvalidProductIdShouldThrowException() {
        ProductRequestDTO request = createProductRequestDTO();

        doReturn(Optional.empty()).when(productRepository).findById(request.getId());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> productService.update(request));
        assertThat(ex.getMessage()).isEqualTo("Could not find product: " + request.getId());


        verify(productRepository).findById(eq(request.getId()));
    }

    @Test
    void updateWhithValidProductIdShouldUpdate() {
        ProductRequestDTO request = createProductRequestDTO();
        Product product = createProduct();

        doReturn(Optional.of(product)).when(productRepository).findById(anyString());

        productService.update(request);
        assertThat(product.getName()).isEqualTo(request.getName());

        verify(productRepository).findById(anyString());
    }

    @Test
    void createWithSQLExceptionShouldThrowException() {
        DataIntegrityViolationException mockEx = mock(DataIntegrityViolationException.class);
        SQLException mockSqlEx = mock(SQLException.class);
        ProductRequestDTO dto = createProductRequestDTO();

        when(mockEx.getRootCause()).thenReturn(mockSqlEx);
        when(((SQLException) mockEx.getRootCause()).getSQLState()).thenReturn("anyState");
        when(productRepository.save(any(Product.class))).thenThrow(mockEx);

        verify(mockEx).getRootCause();
        assertThrows(DataIntegrityViolationException.class, () -> productService.create(dto));
    }

    @Test
    void createWhithDataIntegrityViolationShouldThrowException() {
        DataIntegrityViolationException mockEx = mock(DataIntegrityViolationException.class);
        ProductRequestDTO dto = createProductRequestDTO();

        when(productRepository.save(any(Product.class))).thenThrow(mockEx);

        assertThrows(DataIntegrityViolationException.class, () -> productService.create(dto));
    }

    @Test
    void findByIdWithValidIdShouldReturnDTO() {
        Product product = createProduct();

        when(productRepository.findById(anyString())).thenReturn(Optional.of(product));

        ProductDTO dto = productService.findById("");
        assertThat(dto.getName()).isEqualTo(product.getName());

        verify(productRepository).findById(anyString());
    }

    @Test
    void findByNameWithValidIdShouldReturnDTO() {
        Product product = createProduct();

        when(productRepository.findByName(anyString())).thenReturn(List.of(product));

        List<ProductDTO> dto = productService.findByName(ANY_NAME);
        assertThat(dto.get(0).getName()).isEqualTo(product.getName());

        verify(productRepository).findByName(anyString());
    }

    @Test
    void findByAllWithValidIdShouldReturnDTO() {
        Product product = createProduct();

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductDTO> dto = productService.findAll();
        assertThat(dto.get(0).getName()).isEqualTo(product.getName());

        verify(productRepository).findAll();
    }

    @Test
    void findByIdWithInvalidIdShouldThrowException() {
        doReturn(Optional.empty()).when(productRepository).findById(anyString());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> productService.findById("1"));
        assertThat(ex.getMessage()).isEqualTo("Could not find product: 1");

        verify(productRepository).findById(anyString());
    }

    @Test
    void findByNameWithInvalidIdShouldThrowException() {
        doReturn(new ArrayList<>()).when(productRepository).findByName(anyString());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> productService.findByName("1"));
        assertThat(ex.getMessage()).isEqualTo("Could not find product: 1");

        verify(productRepository).findByName(anyString());
    }


}