package com.example.product.repository;

import com.example.product.dto.ProductInfoDto;
import com.example.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
             SELECT new com.example.product.dto.ProductInfoDto(p.id, p.article, p.description, s.cost, s.currencyUnit, s.amount )
             
                        FROM Storefront s
                        JOIN s.product p
                        WHERE p.id = ?1
            """)
    public ProductInfoDto getProductInfo(Long id);

    @Query(value = """
             SELECT new com.example.product.dto.ProductInfoDto(p.id, p.article, p.description, s.cost, s.currencyUnit, s.amount )
             
                        FROM Storefront s
                        JOIN s.product p
                        WHERE p.article in ?1
            """)
    public List<ProductInfoDto> getArticles(Long[] articles);

}

