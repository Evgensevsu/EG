package ru.evgeniy.tgBot.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.evgeniy.tgBot.entity.ClientOrder;
import ru.evgeniy.tgBot.entity.OrderProduct;
import ru.evgeniy.tgBot.entity.Product;
import ru.evgeniy.tgBot.repository.ClientOrderRep;
import ru.evgeniy.tgBot.repository.OrderProductRep;
import ru.evgeniy.tgBot.repository.ProductRep;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntitiesServiceImpl implements EntitiesService {
    private final ProductRep productRep;
    private final ClientOrderRep clientOrderRep;
    private final OrderProductRep orderProductRep;


    public EntitiesServiceImpl(ProductRep productRep, ClientOrderRep clientOrderRep, OrderProductRep orderProductRep) {
        this.productRep = productRep;
        this.clientOrderRep = clientOrderRep;
        this.orderProductRep = orderProductRep;
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRep.findByCategoryId(categoryId);
    }

    public List<ClientOrder> getClientOrders(Long id) {
        return clientOrderRep.findByClientId(id);
    }

    public List<Product> getClientProducts(Long clientId) {
        return orderProductRep.findDistinctProductsByClient(clientId);
    }

    public List<Product> getTopPopularProducts(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderProductRep.findTopPopularProducts(pageable);
    }

    public List<Product> getCategoryProducts(String categoryName) {
        return productRep.findProductsByCategoryName(categoryName);
    }

    public Product getProductById(int productId) {
        return productRep.findById((long) productId).orElse(null);
    }
}
