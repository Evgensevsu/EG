package ru.evgeniy.tgBot.services;

import ru.evgeniy.tgBot.entity.Client;
import ru.evgeniy.tgBot.entity.ClientOrder;
import ru.evgeniy.tgBot.entity.Product;

import java.util.List;
import java.util.Set;

/**
 * Сервис для работы с сущностями телеграмм-бота
 */

public interface EntitiesService {
    /**
     * Получить список товаров в категории
     * @param id идентификатор категории
     */
    List<Product> getProductsByCategoryId(Long id);
    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    List<ClientOrder> getClientOrders(Long id);
    /**
     * Получить список всех товаров во всех заказах клиента
     * @param id идентификатор клиента
     */
    List<Product> getClientProducts(Long id);
    /**
     * Получить указанное кол-во самых популярных (наибольшее
     * количество штук в заказах) товаров среди клиентов
     * @param limit максимальное кол-во товаров
     */
    List<Product> getTopPopularProducts(Integer limit);

    List<Product> getCategoryProducts(String userText);

    Product getProductById(int productId);
}
