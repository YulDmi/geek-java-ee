package ru.geekbrains.service;

import ru.geekbrains.service.repr.ProductRepr;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Stateful
public class CartServiceImpl implements CartService {
    @EJB
    private ProductService productService;

    private Map<ProductRepr, Integer> productMap = new ConcurrentHashMap<>();


    @Override
    public void add(ProductRepr productRepr) {
        if (productMap.containsKey(productRepr)) {
            productMap.put(productRepr, productMap.get(productRepr) + 1);
        }else {
            productMap.put(productRepr, 1);
        }

    }

    @Override
    public void remove(long id) {
        ProductRepr productRepr = productService.findById(id);
        Iterator<Map.Entry<ProductRepr, Integer>>
                iterator = productMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ProductRepr, Integer> entry = iterator.next();
            if (entry.getKey().equals(productRepr)) {
                iterator.remove();
            }
        }
    }

    @Override
    public Map<ProductRepr, Integer> findAll() {
           return productMap;
        }
    }


