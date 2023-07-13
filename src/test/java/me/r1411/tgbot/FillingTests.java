package me.r1411.tgbot;

import me.r1411.tgbot.entities.*;
import me.r1411.tgbot.repositories.*;
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

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientOrderRepository clientOrderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Test
    @Order(1)
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

    @Test
    @Order(2)
    public void createClientAndOrders() throws FileNotFoundException {
        File clientsFile = new File("src/test/resources/filling_tests_clients_and_orders.yaml");
        Yaml yaml = new Yaml();
        List<Map<String, Object>> clientEntries = yaml.load(new FileInputStream(clientsFile));
        clientEntries.forEach(this::saveClientWithOrders);
    }

    private void saveClientWithOrders(Map<String, Object> clientEntry) {
        Client client = new Client();
        client.setFullName(clientEntry.get("fullName").toString());
        client.setAddress(clientEntry.get("address").toString());
        client.setPhoneNumber(clientEntry.get("phoneNumber").toString());
        client.setExternalId(((Number) clientEntry.get("externalId")).longValue());
        clientRepository.save(client);

        List<Map<String, Object>> clientOrderEntries = (List<Map<String, Object>>) clientEntry.get("orders");
        for (Map<String, Object> orderEntry : clientOrderEntries) {
            ClientOrder clientOrder = new ClientOrder();
            clientOrder.setClient(client);
            clientOrder.setStatus(ClientOrderStatus.valueOf(orderEntry.get("status").toString()));
            clientOrder.setTotal(BigDecimal.ZERO);
            List<Map<String, Object>> orderProductEntries = (List<Map<String, Object>>) orderEntry.get("products");
            List<OrderProduct> orderProducts = orderProductEntries.stream().map(orderProductEntry -> {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setClientOrder(clientOrder);
                orderProduct.setCountProduct((Integer) orderProductEntry.get("count"));
                Product product = productRepository.findById(((Number) orderProductEntry.get("id")).longValue()).orElseThrow();
                orderProduct.setProduct(product);
                return orderProduct;
            }).toList();
            BigDecimal total = orderProducts.stream()
                    .map(op -> op.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            clientOrder.setTotal(total);
            clientOrderRepository.save(clientOrder);
            orderProductRepository.saveAll(orderProducts);
        }
    }
}
