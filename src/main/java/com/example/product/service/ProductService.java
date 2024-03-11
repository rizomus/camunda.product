package com.example.product.service;


import com.example.product.dto.OrderDto;
import com.example.product.dto.OrderReserveDto;
import com.example.product.dto.ProductDto;
import com.example.product.entity.CurrencyUnit;
import com.example.product.entity.Product;
import com.example.product.exception.ArticlesNotFoundException;
import com.example.product.exception.DifferentCurrencyUnitsException;
import com.example.product.exception.NotEnoughAmountForReserve;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productRepository;

//    @PersistenceContext
//    private EntityManager entityManager;

    static int n = 1;

    public ArrayList<Product> getAll() {
        List<Product> products = productRepository.findAll();
        return (ArrayList<Product>) products;
    }


    public long createNewProduct(ProductDto dto) {

        Product product = productRepository.save(new Product(dto));
        return product.getId();
    }


    public Optional<Product> getProduct(long id) {
        return productRepository.findById(id);
    }


    public int testContentLoad() {

        var products = new ArrayList<Product>();
        products.add(Product.builder()
                .article(123L)
                .description("Bong")
                .cost(BigDecimal.valueOf(1000))
                .currencyUnit(CurrencyUnit.RUB)
                .amount(5)
                .build());

        products.add(Product.builder()
                .article(234L)
                .description("Grinder")
                .cost(BigDecimal.valueOf(100))
                .currencyUnit(CurrencyUnit.RUB)
                .amount(2).build());

        products.add(Product.builder()
                .article(420L)
                .description("Weed")
                .cost(BigDecimal.valueOf(400))
                .currencyUnit(CurrencyUnit.RUB)
                .amount(50).build());

        products.add(Product.builder()
                .article(567L)
                .description("Cake")
                .cost(BigDecimal.valueOf(10))
                .currencyUnit(CurrencyUnit.RUB)
                .amount(25).build());

        List<Product> newProducts = productRepository.saveAll(products);
        return newProducts.size();
    }


    public List<ProductDto> getArticles(Long[] articles) {
        List<ProductDto> products = productRepository.findAllByArticleIn(articles);
        return products;
    }


    public void rollbackReserveOrder(ArrayList<OrderDto> order) {
        for (var position : order) {
            productRepository.rollbackReserveArticle(position.article(), position.amount());
        }
    }


    public OrderReserveDto reserveOrder(OrderDto[] orderList) throws InterruptedException, StaleObjectStateException {

        long ORDER_ID = orderList[0].orderId();

        long[] wantedArticles = Arrays.stream(orderList).map(OrderDto::article).mapToLong(Long::longValue).toArray();
        Map<Long, OrderDto> wantedProdMap = Arrays.stream(orderList).collect(Collectors.toMap(OrderDto::article, o -> o));
        Product product = null;
        BigDecimal paymentSum = BigDecimal.valueOf(0);
        HashSet<CurrencyUnit> currencyUnits = new HashSet<>();

        List<Product> existingProducts = productRepository.getAllByArticleIn(wantedArticles);
        Set<Long> existingArticles = existingProducts.stream().map(Product::getArticle).collect(Collectors.toSet());
        Map<Long, Product> existingProductsMap = existingProducts.stream().collect(Collectors.toMap(Product::getArticle, p -> p));

        if (existingProductsMap.size() != orderList.length) {               // some articles not found
            var notFindArticles = wantedProdMap.keySet();
            notFindArticles.removeAll(existingArticles);
            log.error("Articles not found: " + notFindArticles);
            throw new ArticlesNotFoundException("Articles not found: " + notFindArticles);

        } else {                                    // all articles found, check wanted amount and currency
            for (long article : wantedArticles) {
                product = existingProductsMap.get(article);
                int amount = wantedProdMap.get(article).amount();
                currencyUnits.add(product.getCurrencyUnit());
                if (wantedProdMap.get(article).amount() > existingProductsMap.get(article).getAmount()) {
                    log.error("Not enough amount for reserve for article: " + article);
                    throw new NotEnoughAmountForReserve("Not enough amount for reserve for article: " + article);
                }
            }
            if (currencyUnits.size() > 1) throw new DifferentCurrencyUnitsException("Different Currency Units");

//            if (n == 1) {
//                n++;
//                log.debug("--- SLEEPING ---");
//                Thread.sleep(5000);
//                log.debug("--- AWAKING ---");
//            }

            for (long article : wantedArticles) {                   // amount checked, go reserve
                product = existingProductsMap.get(article);
                int amount = wantedProdMap.get(article).amount();
                product.addReserve(ORDER_ID, amount);
                paymentSum = paymentSum.add(BigDecimal.valueOf(product.getCost().intValue() * amount));
            }
        }
        return OrderReserveDto.builder()
                .orderId(ORDER_ID)
                .paymentSum(paymentSum)
                .currencyUnit(currencyUnits.stream().findFirst().get())
                .build();
    }
}
