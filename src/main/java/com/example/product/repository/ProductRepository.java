package com.example.product.repository;

import com.example.product.dto.ProductDto;
import com.example.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    public ProductDto getProductInfo(Long id);

    @Query(value = """
             SELECT p.article, p.description, p.cost, p.currencyUnit, p.amount
             FROM Product p
             WHERE p.article in ?1
            """)
    public List<ProductDto> getArticles(Long[] articles);

    public List<ProductDto> findAllByArticleIn(Long[] articles);


    public Product findByArticle(Long article);


    public List<Product> getAllByArticleIn(long[] articles);


    @Modifying
    @Query("UPDATE Product p SET p.amount = p.amount - ?2 WHERE p.article = ?1")
    public int reserveArticle(long article, int amount);

    @Modifying
    @Query("UPDATE Product p SET p.amount = p.amount + ?2 WHERE p.article = ?1")
    public int rollbackReserveArticle(long article, int amount);

}

