package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Named
@ApplicationScoped
public class ProductRepository {

    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    @PostConstruct
    public void init() {

        save(new Product(null, "product1", new BigDecimal(1000), "description1"));
        save(new Product(null, "product2", new BigDecimal(1010), "description1"));
        save(new Product(null, "product3", new BigDecimal(1500), "description1"));
        save(new Product(null, "product4", new BigDecimal(1570), "description1"));
    }


    public void save(Product product) {
        if (product.getId() == null) {
            product.setId(identity.incrementAndGet());
        }
        products.put(product.getId(), product);
    }

    public void delete(Long id) {
        products.remove(id);
    }

    public Product findById(Long id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());

    }
}
