package ru.evgeniy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRep extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    Product findByName(String бонито);

    List<Product> findProductsByCategoryName(String text);
}
