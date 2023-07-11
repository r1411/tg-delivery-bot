package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductCriteriaRepository {
    List<Product> search(@Nullable String name, @Nullable Long categoryId);
}
