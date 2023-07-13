package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.Category;
import me.r1411.tgbot.repositories.CategoryRepository;
import me.r1411.tgbot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getSubCategories(@Nullable Category parent) {
        if (parent == null) {
            return categoryRepository.findAllByParentIsNull();
        }

        return categoryRepository.findAllByParentId(parent.getId());
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public boolean hasChildren(Category parent) {
        return categoryRepository.existsByParentId(parent.getId());
    }
}
