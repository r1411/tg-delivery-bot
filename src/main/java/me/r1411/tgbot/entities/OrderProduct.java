package me.r1411.tgbot.entities;

import javax.persistence.*;

/**
 * Связующая сущность Заказ-товар
 */
@Entity
public class OrderProduct {
    /**
     * Уникальный ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заказ клиента
     */
    @ManyToOne
    private ClientOrder clientOrder;

    /**
     * Товар в заказе
     */
    @ManyToOne
    private Product product;

    /**
     * Кол-во товара в заказе
     */
    @Column(nullable = false)
    private Integer countProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCountProduct() {
        return countProduct;
    }

    public void setCountProduct(Integer countProduct) {
        this.countProduct = countProduct;
    }
}
