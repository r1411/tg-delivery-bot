package me.r1411.tgbot.rest;

import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/popular")
    public List<Product> getPopular(@RequestParam Integer limit) {
        return productService.getTopPopularProducts(limit);
    }

    @GetMapping("/search")
    public List<Product> searchByNameAndCategory(@RequestParam Optional<String> name, @RequestParam Optional<Long> categoryId) {
        return productService.searchProductsByNameAndCategory(name, categoryId);
    }
}
