package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Named
@ApplicationScoped
public class CategoryRepository {

    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public void save(Category category) {
        if (category.getId() == null) {
            category.setId(identity.incrementAndGet());
        }
        categories.put(category.getId(), category);
    }
    @PostConstruct
    public void init(){
        save(new Category(null, "goods"));
        save(new Category(null, "food"));
    }
    public void delete(Long id) {
        categories.remove(id);
    }

    public Category findById(Long id) {
        return categories.get(id);
    }

    public List<Category> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(categories.values()));

    }
}
