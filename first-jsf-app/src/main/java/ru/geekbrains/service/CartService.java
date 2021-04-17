package ru.geekbrains.service;

import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CartService {

    void add(ProductRepr productRepr);
    void remove(long id);
    List<ProductRepr> findAll();
}
