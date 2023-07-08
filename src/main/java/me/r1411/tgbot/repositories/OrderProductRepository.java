package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Репозиторий для сущностей Заказ-товар
 */
@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
