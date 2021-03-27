package com.geekbrains.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CategoryRepository {

    private final Map<Long, Category> categories = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public void save(Category category) {
        if (category.getId() == null) {
            category.setId(identity.incrementAndGet());
        }
        categories.put(category.getId(), category);
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
