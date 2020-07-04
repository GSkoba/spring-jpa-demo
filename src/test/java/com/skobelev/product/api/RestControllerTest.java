package com.skobelev.product.api;

import com.skobelev.product.api.extension.JsonData;
import com.skobelev.product.api.extension.JsonExtensions;
import com.skobelev.product.api.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(
        classes = ApiApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(JsonExtensions.class)
public class RestControllerTest {

    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    @DisplayName("Check get all")
    void getAllTest() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                "/api/product", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> list = response.getBody();
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    @DisplayName("Check get by id")
    void getByIdTest() {
        ResponseEntity<Product> response = restTemplate.exchange(
                "/api/product/1", HttpMethod.GET, null, Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Product product = response.getBody();
        assertNotNull(product);
        assertNotNull(product.getId());
        assertNotNull(product.getBrand());
        assertNotNull(product.getCategories());
        assertNotNull(product.getName());
    }

    @Test
    @DisplayName("Check create new valid product")
    void createNewProduct(@JsonData("product.json") Product product) {
        ResponseEntity<Product> response = restTemplate.postForEntity("/api/product", product, Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Product actualProduct = response.getBody();
        assertNotNull(actualProduct);
        assertNotNull(actualProduct.getId());
        assertNotNull(actualProduct.getBrand());
        assertNotNull(actualProduct.getCategories());
        assertNotNull(actualProduct.getName());
    }

    @Test
    @DisplayName("Check create new not valid product")
    void createNewNotValidProduct(@JsonData("not_valid_product.json") Product product) {
        ResponseEntity<Product> response = restTemplate.postForEntity("/api/product", product, Product.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Product actualProduct = response.getBody();
        assertNotNull(actualProduct);
        assertNull(actualProduct.getId());
        assertNull(actualProduct.getBrand());
        assertNull(actualProduct.getCategories());
        assertNull(actualProduct.getName());
    }

    @Test
    @DisplayName("Check update exist product")
    void updateTest(@JsonData("product.json") Product product) {
        product.setId(1);
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<Product> response = restTemplate.exchange(
                "/api/product", HttpMethod.PATCH, new HttpEntity<Product>(product), Product.class);
        Product actualProduct = response.getBody();
        assertNotNull(actualProduct);
        assertNotNull(actualProduct.getId());
        assertEquals(1, actualProduct.getId());
        assertNotNull(actualProduct.getBrand());
        assertNotNull(actualProduct.getCategories());
        assertNotNull(actualProduct.getName());
    }

    @Test
    @DisplayName("Check delete exist product")
    void deleteTest() {
        Integer id = 2;
        restTemplate.exchange("/api/product/" + id, HttpMethod.DELETE, null, Void.class);
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                "/api/product", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> products = response.getBody();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        products.forEach(product -> assertNotEquals(id, product.getId()));
    }
}
