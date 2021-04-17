package ru.geekbrains.controller;

import ru.geekbrains.persist.Category;
import ru.geekbrains.persist.CategoryRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.service.ProductService;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductController implements Serializable {

    @EJB
    private ProductService productService;

    @Inject
    private CategoryRepository categoryRepository;

    private ProductRepr productRepr;
    private List<ProductRepr> productList;
    private List<Category> categoryList;

    public List<Category> getCategories() {
        return categoryList;
    }

    public void setCategories(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.productList = productService.findAll();
        this.categoryList = categoryRepository.findAll();
    }


    public ProductRepr getProduct() {
        return productRepr;
    }

    public void setProduct(ProductRepr productRepr) {
        this.productRepr = productRepr;
    }

    public List<ProductRepr> findAll() {
        return productList;
    }

    public String editProduct(ProductRepr productRepr) {
        this.productRepr = productRepr;
        return "/product_form.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductRepr productRepr) {
        productService.delete(productRepr.getId());
    }

    public String saveProduct() {
        productService.save(productRepr);
        return "/product.xhtml?faces-redirect=true";
    }

    public String addProduct() {
        this.productRepr = new ProductRepr();
        return "/product_form.xhtml?faces-redirect=true";
    }
}