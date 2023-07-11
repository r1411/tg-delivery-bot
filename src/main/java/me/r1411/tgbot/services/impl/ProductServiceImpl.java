package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.repositories.ProductRepository;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductsByCategoryId(Long id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Override
    public List<Product> getClientProducts(Long id) {
        return productRepository.findAllByClientId(id);
    }

    @Override
    public List<Product> getTopPopularProducts(Integer limit) {
        return productRepository.findTopPopularProducts(PageRequest.of(0, limit));
    }

    @Override
    public List<Product> searchProductsByName(String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }
}