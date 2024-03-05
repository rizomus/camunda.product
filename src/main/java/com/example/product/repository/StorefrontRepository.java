package com.example.product.repository;

import com.example.product.entity.Storefront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StorefrontRepository extends JpaRepository<Storefront, Long> {


    @Query("""
            UPDATE Storefront s
            SET s.amount = s.amount - 1
            WHERE s.product.id =
                (SELECT p.id FROM Product p
                 WHERE p.article = ?1)

            """)
    public int reserveProductForOrder(Long article);

}
