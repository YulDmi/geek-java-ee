package ru.geekbrains.service;

import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateful
public class CartServiceImpl implements CartService {
    @EJB
    private ProductService productService;

    private final Map<Long, ProductRepr> productMap = new ConcurrentHashMap<>();


    @Override
    public void add(ProductRepr productRepr) {
       productMap.put(productRepr.getId(), productRepr);

    }

    @Override
    public void remove(long id) {
        productMap.remove(id);
    }

    @Override
    public List<ProductRepr> findAll() {
           return new ArrayList<>(productMap.values());
        }
    }


