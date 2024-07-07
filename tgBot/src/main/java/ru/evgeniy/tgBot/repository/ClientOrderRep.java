package ru.evgeniy.tgBot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.ClientOrder;

@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRep extends JpaRepository<ClientOrder, Long> {

}
