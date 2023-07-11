package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
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
     * Найти товары по части названия, ID категории, либо по двум параметрам сразу
     * @param name       Подстрока названия
     * @param categoryId ID категории
     */
    List<Product> searchProducts(Optional<String> name, Optional<Long> categoryId);
}
