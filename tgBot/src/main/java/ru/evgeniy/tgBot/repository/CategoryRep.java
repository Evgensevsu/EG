package ru.evgeniy.tgBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.evgeniy.tgBot.entity.Category;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRep extends JpaRepository<Category, Long> {
    List<Category> findCategoriesByParentId(Long parentId);
    List<Category> findCategoriesByParentIdIsNull();
}