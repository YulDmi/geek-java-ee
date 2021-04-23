package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.service.repr.CategoryRepr;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {

    void save(CategoryRepr categoryRepr);

    void delete(Long id);

    CategoryRepr findById(Long id);

    List<CategoryRepr> findAll();

    long count();
}
