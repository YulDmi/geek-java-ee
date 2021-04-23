package ru.geekbrains.service;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.rest.CategoryResource;
import ru.geekbrains.rest.ProductResource;
import ru.geekbrains.service.repr.CategoryRepr;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService, CategoryResource {


    @EJB
    private CategoryRepository categoryRepository;

    @Override
    @TransactionAttribute
    public void save(CategoryRepr categoryRepr) {
        categoryRepository.save(
                new Category(categoryRepr.getId(),
                        categoryRepr.getName()
                ));
    }

    @Override
    @TransactionAttribute
    public void delete(Long id) {
        categoryRepository.delete(id);
    }

    @Override
    public CategoryRepr findById(Long id) {
        return createCategoryRepr(categoryRepository.findById(id));
    }

    @Override
    public List<CategoryRepr> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryServiceImpl::createCategoryRepr)
                .collect(Collectors.toList());
    }


    @Override
    public void insert(CategoryRepr categoryRepr) {
        if (categoryRepr.getId() != null) {
            throw new IllegalArgumentException("Not null id in insert Category");
        }
        save(categoryRepr);
    }

    @Override
    public void update(CategoryRepr categoryRepr) {
        if (categoryRepr.getId() == null) {
            throw new IllegalArgumentException("Null id in update Category");
        }
        save(categoryRepr);
    }


    @Override
    public long count() {
        return categoryRepository.count();
    }

    private static CategoryRepr createCategoryRepr(Category category) {
        return new CategoryRepr(
                category.getId(),
                category.getName()
        );
    }
}
