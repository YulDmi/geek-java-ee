package ru.geekbrains.controller;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.repr.CategoryRepr;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductController implements Serializable {
    @EJB
    private ProductService productService;

    @EJB
    private CategoryService categoryService;

    private ProductRepr product;
    private List<ProductRepr> productList;
    private List<CategoryRepr> categoryList;

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.productList = productService.findAll();
        this.categoryList = categoryService.findAll();
    }

    public ProductRepr getProduct() {
        return product;
    }

    public void setProduct(ProductRepr product) {
        this.product = product;
    }

    public List<CategoryRepr> getCategories() {
        return categoryList;
    }

    public void setCategories(List<CategoryRepr> categoryList) {
        this.categoryList = categoryList;
    }

    public List<ProductRepr> findAll() {
        return productList;
    }

    public String editProduct(ProductRepr product) {
        this.product = product;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductRepr product) {
        productService.delete(product.getId());
    }

    public String saveProduct() {
        productService.save(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.product = new ProductRepr();
        return "/product_form.xhtml?faces-redirect=true";
    }
}
