package ru.geekbrains.service;

import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductServiceImpl implements ProductService {

    @EJB
    private ProductRepository productRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @TransactionAttribute
    public void save(ProductRepr productRepr) {
        productRepository.save(
                new Product(productRepr.getId(),
                        productRepr.getName(),
                        productRepr.getPrice(),
                        productRepr.getDescription(),
                        categoryRepository.getReference(productRepr.getCategoryId())
                ));
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        productRepository.delete(id);
    }

    @Override
    public ProductRepr findById(Long id) {
       return createProductRepr(productRepository.findById(id));
    }

    @Override
    public List<ProductRepr> findAll() {
        return productRepository.findAll().stream()
                .map(ProductServiceImpl ::createProductRepr)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return productRepository.count();
    }

    private static ProductRepr createProductRepr(Product product) {
        return new ProductRepr(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
