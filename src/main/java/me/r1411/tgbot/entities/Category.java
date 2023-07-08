package me.r1411.tgbot.entities;

import javax.persistence.*;

/**
 * Сущность Категория
 */
@Entity
public class Category {
    /**
     * Уникальный ID категории
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название категории
     */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /**
     * Родительская категория
     */
    @ManyToOne
    private Category parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
