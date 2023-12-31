package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущностей Категория
 */
@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);

    List<Category> findAllByParentId(Long id);

    List<Category> findAllByParentIsNull();

    boolean existsByParentId(Long id);
}
