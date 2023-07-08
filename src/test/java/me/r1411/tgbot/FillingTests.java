package me.r1411.tgbot;

import me.r1411.tgbot.entities.Category;
import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.repositories.CategoryRepository;
import me.r1411.tgbot.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
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
public class FillingTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void createMenu() throws FileNotFoundException {
        File categoriesFile = new File("src/test/resources/filling_tests_menu.yaml");
        Yaml yaml = new Yaml();
        List<Map<String, Object>> categoryEntries = yaml.load(new FileInputStream(categoriesFile));
        parseMenuRecursively(categoryEntries, null);
    }

    private void parseMenuRecursively(List<Map<String, Object>> categoryEntries, Category parent) {
        for (Map<String, Object> categoryEntry : categoryEntries) {
            Category category = new Category();
            category.setName(categoryEntry.get("name").toString());
            if (parent != null)
                category.setParent(parent);

            categoryRepository.save(category);

            if (categoryEntry.containsKey("products")) {
                List<Map<String, Object>> productEntries = (List<Map<String, Object>>) categoryEntry.get("products");
                List<Product> products = productEntries.stream().map(entry -> {
                    Product product = new Product();
                    product.setName(entry.get("name").toString());
                    product.setDescription(entry.get("description").toString());
                    product.setPrice(new BigDecimal(entry.get("price").toString()));
                    product.setCategory(category);
                    return product;
                }).toList();

                productRepository.saveAll(products);
            }

            if (categoryEntry.containsKey("subcategories")) {
                List<Map<String, Object>> subs = (List<Map<String, Object>>) categoryEntry.get("subcategories");
                parseMenuRecursively(subs, category);
            }
        }
    }
}
