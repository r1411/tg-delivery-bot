package me.r1411.tgbot.repositories;

import me.r1411.tgbot.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductCriteriaRepository {
    private final EntityManager entityManager;

    @Autowired
    public ProductCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Найти товары по части названия, ID категории, либо по двум параметрам сразу
     * @param name       Подстрока названия
     * @param categoryId ID категории
     */
    public List<Product> search(Optional<String> name, Optional<Long> categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name.isPresent()) {
            predicates.add(cb.like(cb.lower(product.get("name")), "%" + name.get().toLowerCase() + "%"));
        }

        if (categoryId.isPresent()) {
            predicates.add(cb.equal(product.get("category").get("id"), categoryId.get()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Product> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
