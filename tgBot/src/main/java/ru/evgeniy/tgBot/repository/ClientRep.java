package ru.evgeniy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.Client;

@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRep extends JpaRepository<Client, Long> {

}
