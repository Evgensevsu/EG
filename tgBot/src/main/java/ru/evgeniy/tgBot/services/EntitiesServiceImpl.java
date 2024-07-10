package ru.evgeniy.tgBot.services;

import jakarta.transaction.Transactional;
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

    public List<Product> getClientProducts(Long id) {
        // Получение всех продуктов по клиенту
        List<OrderProduct> orderProducts = orderProductRep.findByClientOrderClientId(id);
        // Извлечение продуктов из списка заказанных продуктов
        List<Product> products = orderProducts.stream()
                .map(OrderProduct::getProduct)
                .distinct()
                .collect(Collectors.toList());
        return products;
    }

    public List<Product> getTopPopularProducts(Integer limit) {
        // Получение всех OrderProduct
        List<OrderProduct> orderProducts = orderProductRep.findAll();
        // Группировка OrderProduct по продукту и подсчет общего количества каждого продукта
        Map<Product, Integer> productCountMap = orderProducts.stream()
                .collect(Collectors.groupingBy(OrderProduct::getProduct, Collectors.summingInt(OrderProduct::getCountProduct)));
        // Сортировка по количеству в порядке убывания
        List<Map.Entry<Product, Integer>> sortedProductEntries = productCountMap.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());
        // Извлечение топ-N продуктов
        List<Product> topProducts = sortedProductEntries.stream()
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        return topProducts;
    }
}
