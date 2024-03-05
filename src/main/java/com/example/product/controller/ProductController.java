package com.example.product.controller;

import com.example.product.dto.OrderDto;
import com.example.product.dto.ProductInfoDto;
import com.example.product.dto.StorefrontDto;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import com.example.product.service.StorfontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    StorfontService storefrontService;


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        ArrayList<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInfoDto> getProduct(@PathVariable long id) {
        ProductInfoDto productInfo = null;
        try {
            productInfo = productService.getProductInfo(id).orElseThrow(() -> new RuntimeException("No such productInfo") );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        productService.getProductInfo(productInfo.getId());
        return new ResponseEntity<>(productInfo, HttpStatus.OK);
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ProductInfoDto>> getArticles(@RequestBody Long[] articles) {

        Arrays.stream(articles).forEach(System.out::println);
        List<ProductInfoDto> products = productService.getArticles(articles);

        System.out.println(products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/order")
    public void newOrder(@RequestBody OrderDto[] order) {
        Arrays.stream(order).forEach(System.out::println);


    }

//    @PostMapping("/new")
//    public ResponseEntity<Long> createNewProduct(@RequestBody ProductDto productDto) {
//        long id = productService.createNewProduct(productDto);
//        return new ResponseEntity<>(id, HttpStatus.CREATED);
//    }

    @PostMapping("/new")
    public ResponseEntity<Long> createNewProduct(@RequestBody StorefrontDto storefrontDto) {
        long id = storefrontService.createStorefront(storefrontDto);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @PostMapping("/test-content-load")
    public ResponseEntity<Long> createNewProduct() {
        long amount = productService.testContentLoad();
        return new ResponseEntity<>(amount, HttpStatus.CREATED);
    }
}
