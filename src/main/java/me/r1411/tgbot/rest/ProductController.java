package me.r1411.tgbot.rest;

import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/search", params = "categoryId")
    public List<Product> searchProducts(@RequestParam Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/popular")
    public List<Product> getPopular(@RequestParam Integer limit) {
        return productService.getTopPopularProducts(limit);
    }

    @GetMapping(value = "/search", params = "name")
    public List<Product> searchByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }
}
