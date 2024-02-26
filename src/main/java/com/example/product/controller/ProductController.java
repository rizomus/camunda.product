package com.example.product.controller;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        ArrayList<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = null;
        try {
            product = productService.getProduct(id).orElseThrow(() -> new RuntimeException("No such product") );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Long> createNewProduct(@RequestBody ProductDto productDto) {
        long id = productService.createNewProduct(productDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/test-content-load")
    public ResponseEntity<Long> createNewProduct() {
        long amount = productService.testContentLoad();
        return new ResponseEntity<>(amount, HttpStatus.CREATED);
    }
}
