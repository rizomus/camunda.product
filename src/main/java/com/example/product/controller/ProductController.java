package com.example.product.controller;

import com.example.product.dto.OrderDto;
import com.example.product.dto.OrderReserveDto;
import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import com.example.product.exception.ArticlesNotFoundException;
import com.example.product.exception.DifferentCurrencyUnitsException;
import com.example.product.exception.NotEnoughAmountForReserve;
import com.example.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        ArrayList<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        Product product = null;
        try {
            product = productService.getProduct(id).orElseThrow(() -> new RuntimeException("No such productInfo") );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(product.getDto(), HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ProductDto>> getArticles(@RequestBody Long[] articles) {

        Arrays.stream(articles).forEach(System.out::println);
        List<ProductDto> products = productService.getArticles(articles);

        System.out.println(products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/reserve")
    public ResponseEntity<Object> newOrder(@RequestBody OrderDto[] orderList) throws InterruptedException {

        for (var order : orderList) {
            log.debug("position: " + order);
        }

        OrderReserveDto orderReserveDto = null;

        try {
            orderReserveDto = productService.reserveOrder(orderList);

        } catch (ArticlesNotFoundException | NotEnoughAmountForReserve e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (ObjectOptimisticLockingFailureException | DifferentCurrencyUnitsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok().body(orderReserveDto);
    }


    @PostMapping("/new")
    public ResponseEntity<Long> createNewProduct(@RequestBody ProductDto dto) {

        long id = productService.createNewProduct(dto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    @PostMapping("/test-content-load")
    public ResponseEntity<Long> createNewProduct() {
        long amount = productService.testContentLoad();
        return new ResponseEntity<>(amount, HttpStatus.CREATED);
    }
}
