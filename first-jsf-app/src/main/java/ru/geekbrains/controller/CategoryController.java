package ru.geekbrains.controller;

import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.repr.CategoryRepr;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CategoryController implements Serializable {

    @Inject
    private CategoryService categoryService;

    private CategoryRepr category;
    private List<CategoryRepr> categoryList;

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.categoryList = categoryService.findAll();
    }

    public CategoryRepr getCategory() {
        return category;
    }

    public void setCategory(CategoryRepr category) {
        this.category = category;
    }

    public List<CategoryRepr> findAll() {
        return categoryList;
    }

    public String editCategory(CategoryRepr category) {
        this.category = category;
        return "/category_form.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryRepr category) {
        categoryService.delete(category.getId());
    }

    public String saveCategory() {
        categoryService.save(category);
        return "/category.xhtml?faces-redirect=true";
    }

    public String addCategory() {
        this.category = new CategoryRepr();
        return "/category_form.xhtml?faces-redirect=true";
    }
}
