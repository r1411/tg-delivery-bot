package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.ClientOrder;
import me.r1411.tgbot.entities.ClientOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущностей Заказ клиента
 */
@RepositoryRestResource(collectionResourceRel = "clientOrders", path = "clientOrders")
public interface ClientOrderRepository extends JpaRepository<ClientOrder, Long> {
    /**
     * Найти заказы клиента
     * @param id ID клиента
     */
    List<ClientOrder> findAllByClientId(Long id);

    /**
     * Найти первый заказ с определенным статусом
     * @param status Статус
     */
    Optional<ClientOrder> findFirstByStatusAndClientId(ClientOrderStatus status, Long clientId);
}
