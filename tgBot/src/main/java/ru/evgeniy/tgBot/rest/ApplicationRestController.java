package ru.evgeniy.tgBot.rest;

import org.springframework.web.bind.annotation.*;
import ru.evgeniy.tgBot.entity.ClientOrder;
import ru.evgeniy.tgBot.entity.Product;
import ru.evgeniy.tgBot.services.EntitiesServiceImpl;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {
    private final EntitiesServiceImpl entitiesServiceImpl;

    public ApplicationRestController(EntitiesServiceImpl entitiesServiceImpl) {
        this.entitiesServiceImpl = entitiesServiceImpl;
    }

    @GetMapping("/products/search")
    public List<Product> getProductsByCategoryId(@RequestParam Long categoryId) {
        return entitiesServiceImpl.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/clients/{id}/orders")
    public List<ClientOrder> getClientOrders(@PathVariable Long id) {
        return entitiesServiceImpl.getClientOrders(id);
    }

    @GetMapping("/clients/{id}/products")
    public List<Product> getClientProducts(@PathVariable Long id) {
        return entitiesServiceImpl.getClientProducts(id);
    }

    @GetMapping("/products/popular")
    public List<Product> getTopPopularProducts(@RequestParam Integer limit) {
        return entitiesServiceImpl.getTopPopularProducts(limit);
    }
}
