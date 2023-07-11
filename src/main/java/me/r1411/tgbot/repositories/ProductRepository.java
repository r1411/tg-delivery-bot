package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Репозиторий для сущностей Товар
 */
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository {
    /**
     * Получить все товары в категории
     * @param id ID категории
     */
    List<Product> findAllByCategoryId(Long id);

    /**
     * Найти все продукты по подстроке названия
     * @param name подстрока названия
     */
    List<Product> findAllByNameContainingIgnoreCase(String name);

    /**
     * Получить все товары во всех заказах клиента
     * @param id ID клиента
     */
    @Query("select distinct op.product from OrderProduct op where op.clientOrder.client.id = :id")
    List<Product> findAllByClientId(@Param("id") Long id);

    /**
     * Получить топ самых заказываемых товаров
     * @param pageable Страничная навигация
     */
    @Query("select op.product, sum(op.countProduct) from OrderProduct op group by (op.product.id) order by sum(op.countProduct) desc")
    List<Product> findTopPopularProducts(Pageable pageable);
}
