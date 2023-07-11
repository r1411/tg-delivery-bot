package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Product;

import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> search(String name, Long categoryId);
}
