package me.r1411.tgbot.services.impl;

import me.r1411.tgbot.entities.Product;
import me.r1411.tgbot.repositories.ProductRepository;
import me.r1411.tgbot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
    public List<Product> searchProducts(@Nullable String name, @Nullable Long categoryId) {
        return productRepository.search(name, categoryId);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
}
