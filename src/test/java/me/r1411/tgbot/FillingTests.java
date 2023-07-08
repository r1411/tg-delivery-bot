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
        List<Map<String, String>> categoryEntries = yaml.load(new FileInputStream(categoriesFile));

        // Сначала создаем корневые категории
        List<Category> rootCategories = categoryEntries.stream()
                .filter(entry -> !entry.containsKey("parent"))
                .map(entry -> {
                    Category cat = new Category();
                    cat.setName(entry.get("name"));
                    return cat;
                }).toList();

        categoryRepository.saveAll(rootCategories);

        // Теперь создаем подкатегории
        List<Category> subCategories = categoryEntries.stream()
                .filter(entry -> entry.containsKey("parent"))
                .map(entry -> {
                    Category cat = new Category();
                    cat.setName(entry.get("name"));
                    cat.setParent(categoryRepository.findByName(entry.get("parent")).orElseThrow());
                    return cat;
                }).toList();

        categoryRepository.saveAll(subCategories);
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
