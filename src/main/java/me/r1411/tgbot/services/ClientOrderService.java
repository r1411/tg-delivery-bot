package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Client;
import me.r1411.tgbot.entities.ClientOrder;
import me.r1411.tgbot.entities.OrderProduct;
import me.r1411.tgbot.entities.Product;

import java.util.List;

public interface ClientOrderService {
    /**
     * Получить список заказов клиента
     * @param id идентификатор клиента
     */
    List<ClientOrder> getClientOrders(Long id);

    /**
     * Сохранить заказ клиента в БД
     * @param clientOrder Заказ
     */
    ClientOrder saveClientOrder(ClientOrder clientOrder);

    /**
     * Получить текущий открытый заказ клиента, или создать новый, если открытых нет
     * @param client Клиент
     */
    ClientOrder getOrCreateOpenOrder(Client client);

    /**
     * Добавить продукт в заказ
     * @param order Заказ
     * @param product Продукт
     */
    void addProductToOrder(ClientOrder order, Product product, int count);

    /**
     * Получить список позиций в заказе
     * @param order Заказ
     */
    List<OrderProduct> getProductsInOrder(ClientOrder order);

    /**
     * Убрать все позиции из заказа
     * @param order Заказ
     */
    void clearOrder(ClientOrder order);
}
