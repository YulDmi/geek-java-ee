package ru.geekbrains.service;

import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductService {

    void save(ProductRepr product);

    void delete(Long id);

    ProductRepr findById(Long id);

    List<ProductRepr> findAll();

    long count();
}
