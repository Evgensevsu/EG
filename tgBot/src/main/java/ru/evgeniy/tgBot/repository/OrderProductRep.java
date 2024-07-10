package ru.evgeniy.tgBot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.OrderProduct;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRep extends JpaRepository<OrderProduct, Long> {

    List<OrderProduct> findByClientOrderClientId(Long id);
}
