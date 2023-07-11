package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Репозиторий для сущностей Клиент
 */
@RepositoryRestResource(collectionResourceRel = "clients", path = "clients")
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Найти клиентов по подстроке полного имени
     * @param name Подстрока полного имени
     */
    List<Client> findAllByFullNameContainingIgnoreCase(String name);
}
