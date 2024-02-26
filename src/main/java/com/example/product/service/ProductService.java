package com.example.product.service;


import com.example.product.dto.ProductDto;
import com.example.product.entity.CurrencyUnit;
import com.example.product.entity.Product;
import com.example.product.entity.Storefront;
import com.example.product.repository.ProductRepository;
import com.example.product.repository.StorefrontRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StorefrontRepository storefrontRepository;


    public ArrayList<Product> getAll() {
        List<Product> products = productRepository.findAll();
        return (ArrayList<Product>) products;
    }

    public Long createNewProduct(ProductDto productDto) {

        Product product = productRepository.save(new Product(productDto));
        return product.getId();
    }

    public Optional<Product> getProduct(long id) {
        return productRepository.findById(id);
    }

    public int testContentLoad() {

        var products = new ArrayList<Product>();
        var storefronts = new ArrayList<Storefront>();

        products.add(Product.builder()
                .article(123L)
                .description("Bong")
                .build());

        products.add(Product.builder()
                .article(234L)
                .description("Grinder").build());

        products.add(Product.builder()
                .article(420L)
                .description("Weed").build());

        products.add(Product.builder()
                .article(567L)
                .description("Cake").build());

        List<Product> newProducts = productRepository.saveAll(products);

        for (var prod :
                products) {
            storefronts.add(Storefront.builder()
                    .product(prod)
                    .cost(BigDecimal.valueOf(Math.random() * 100))
                    .currencyUnit(CurrencyUnit.getRundom())
                    .amount(new Random().nextInt(0, 20))
                    .build());
        }

        storefrontRepository.saveAll(storefronts);

        return newProducts.size();
    }
}
