package me.r1411.tgbot.entities;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Сущность Товар
 */
@Entity
public class Product {
    /**
     * Уникальный ID товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Категория товара
     */
    @ManyToOne
    private Category category;

    /**
     * Название товара
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Описание товара
     */
    @Column(nullable = false, length = 400)
    private String description;

    /**
     * Стоимость товара
     */
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
