package me.r1411.tgbot.services;

import me.r1411.tgbot.entities.Category;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    /**
     * Найти все подкатегории у категории. Если передан null, возвращает категории верхнего уровня
     * @param parent Родительская категория
     */
    List<Category> getCategories(@Nullable Category parent);

    /**
     * Поиск категории по имени без учета регистра
     * @param name Имя категории
     */
    Optional<Category> findCategoryByName(String name);

    /**
     * Проверить, есть ли подкатегории у переданной
     * @param parent Родительская категория
     */
    boolean hasChildren(Category parent);
}
