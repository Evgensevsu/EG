package ru.evgeniy.tgBot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.OrderProduct;
import ru.evgeniy.tgBot.entity.Product;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRep extends JpaRepository<OrderProduct, Long> {

    @Query("SELECT op.product FROM OrderProduct op GROUP BY op.product ORDER BY SUM(op.countProduct) DESC")
    List<Product> findTopPopularProducts(org.springframework.data.domain.Pageable pageable);

    @Query("SELECT DISTINCT op.product FROM OrderProduct op WHERE op.clientOrder.client.id = :id")
    List<Product> findDistinctProductsByClient(Long id);
}
