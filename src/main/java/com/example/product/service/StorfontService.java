package com.example.product.service;

import com.example.product.dto.ProductDto;
import com.example.product.dto.StorefrontDto;
import com.example.product.entity.Product;
import com.example.product.entity.Storefront;
import com.example.product.repository.StorefrontRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StorfontService {

    @Autowired
    StorefrontRepository storefrontRepository;
    @Autowired
    ProductService productService;


    public long createStorefront(StorefrontDto storefrontDto) {

        ProductDto productDto = storefrontDto.product().getDto();
        Product prod = productService.createNewProduct(productDto);
        Storefront storefront = new Storefront(storefrontDto);
        storefront.setProduct(prod);                                // иначе Hibernate ругается на transient entity
        Storefront sf = storefrontRepository.save(storefront);
        return sf.getId();
    }
}
