package ru.evgeniy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.Category;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRep extends JpaRepository<Category, Long> {

}