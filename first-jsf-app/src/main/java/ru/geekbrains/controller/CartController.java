package ru.geekbrains.controller;

import ru.geekbrains.service.CartService;
import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class CartController implements Serializable {

    @EJB
    private CartService cartService;

    public void add(ProductRepr product){
        cartService.add(product);
    }

    public void remove(ProductRepr product){
        cartService.remove(product.getId());
    }

    public List<ProductRepr> findAll(){
        return cartService.findAll();
    }
}
