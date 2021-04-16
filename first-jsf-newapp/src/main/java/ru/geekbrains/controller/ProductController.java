package ru.geekbrains.controller;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductController implements Serializable {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    private Product product;
    private List<Product> productList;
    private List<Category> categoryList;

    public List<Category> getCategories() {
        return categoryList;
    }

    public void setCategories(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.productList = productRepository.findAll();
        this.categoryList = categoryRepository.findAll();
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> findAll() {
        return productList;
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product.getId());
    }

    public String saveProduct() {
        productRepository.save(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.product = new Product();
        return "/product_form.xhtml?faces-redirect=true";
    }
}