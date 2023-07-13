package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    /**
     * Найти всех клиентов по подстроке имени
     * @param name подстрока имени клиента
     */
    List<Client> searchClientsByName(String name);

    /**
     * Найти клиента по внешнему ID
     * @param id Внешний ID клиента
     */
    Optional<Client> findClientByExternalId(Long id);

    /**
     * Сохранить клиента в БД
     * @param client Клиент
     */
    Client saveClient(Client client);
}
