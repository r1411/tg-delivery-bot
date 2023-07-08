package me.r1411.tgbot;

import me.r1411.tgbot.entities.Category;
import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.repositories.CategoryRepository;
import me.r1411.tgbot.repositories.ProductRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FillingTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Order(1)
    void createCategories() throws FileNotFoundException {
        File categoriesFile = new File("src/test/resources/filling_tests_categories.yaml");
        Yaml yaml = new Yaml();
        List<Map<String, Object>> categoryEntries = yaml.load(new FileInputStream(categoriesFile));
        List<Category> categories = parseCategoriesRecursively(categoryEntries, null, new ArrayList<>());

        categoryRepository.saveAll(categories);
    }


    private List<Category> parseCategoriesRecursively(List<Map<String, Object>> categoryEntries, Category parent, List<Category> accumulator) {
        for (Map<String, Object> categoryEntry : categoryEntries) {
            Category category = new Category();
            category.setName(categoryEntry.get("name").toString());
            if (parent != null)
                category.setParent(parent);

            accumulator.add(category);

            if (categoryEntry.containsKey("subcategories")) {
                List<Map<String, Object>> subs = (List<Map<String, Object>>) categoryEntry.get("subcategories");
                parseCategoriesRecursively(subs, category, accumulator);
            }
        }

        return accumulator;
    }

    @Test
    @Order(2)
    void createProducts() throws FileNotFoundException {
        File categoriesFile = new File("src/test/resources/filling_tests_products.yaml");
        Yaml yaml = new Yaml();
        List<Map<String, Object>> productEntries = yaml.load(new FileInputStream(categoriesFile));

        List<Product> products = productEntries.stream()
                .map(entry -> {
                    Product product = new Product();
                    product.setName(entry.get("name").toString());
                    product.setDescription(entry.get("description").toString());
                    product.setPrice(new BigDecimal(entry.get("price").toString()));
                    product.setCategory(categoryRepository.findByName(entry.get("category").toString()).orElseThrow());
                    return product;
                }).toList();

        productRepository.saveAll(products);
    }
}
