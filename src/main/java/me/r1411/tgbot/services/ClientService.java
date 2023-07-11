package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.entities.ClientOrder;

import java.util.List;

public interface ClientService {
    /**
     * Найти всех клиентов по подстроке имени
     * @param name подстрока имени клиента
     */
    List<Client> searchClientsByName(String name);

    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    List<ClientOrder> getClientOrders(Long id);
}
