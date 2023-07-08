package me.r1411.tgbot.entities;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Сущность Заказ клиента
 */
@Entity
public class ClientOrder {
    /**
     * Уникальный ID заказа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Клиент, сделавший заказ
     */
    @ManyToOne
    private Client client;

    /**
     * Текущий статус заказа
     */
    @Column(nullable = false)
    private Integer status;

    /**
     * Сумма по заказу
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
