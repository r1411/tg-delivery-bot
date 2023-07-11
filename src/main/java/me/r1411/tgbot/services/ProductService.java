package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Product;

import java.util.List;

public interface ProductService {
    /**
     * Получить список товаров в категории
     * @param id идентификатор категории
     */
    List<Product> getProductsByCategoryId(Long id);

    /**
     * Получить список всех товаров во всех заказах клиента
     * @param id идентификатор клиента
     */
    List<Product> getClientProducts(Long id);

    /**
     * Получить указанное кол-во самых популярных (наибольшее
     * количество штук в заказах) товаров среди клиентов
     * @param limit максимальное кол-во товаров
     */
    List<Product> getTopPopularProducts(Integer limit);

    /**
     * Найти все продукты по подстроке названия
     * @param name подстрока названия продукта
     */
    List<Product> searchProductsByName(String name);
}
