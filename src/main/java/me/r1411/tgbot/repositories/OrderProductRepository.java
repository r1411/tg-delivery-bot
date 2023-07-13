package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущностей Заказ-товар
 */
@RepositoryRestResource(collectionResourceRel = "orderProducts", path = "orderProducts")
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    /**
     * Найти позицию в заказе по ID товара и ID заказа
     * @param clientOrderId ID заказа
     * @param productId ID товара
     */
    Optional<OrderProduct> findByClientOrderIdAndProductId(Long clientOrderId, Long productId);

    /**
     * Получить список позиций в заказе
     * @param clientOrderId ID заказа
     */
    List<OrderProduct> findAllByClientOrderId(Long clientOrderId);

    /**
     * Удалить все позиции из заказа
     * @param clientOrderId ID заказа
     */
    void deleteAllByClientOrderId(Long clientOrderId);
}
