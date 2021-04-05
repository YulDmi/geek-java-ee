package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Named
@ApplicationScoped
public class ProductRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    @PostConstruct
    public void init() {
        if (count() == 0) {
            try {
                ut.begin();
                save(new Product(null, "product1", new BigDecimal(1000), "description1"));
                save(new Product(null, "product2", new BigDecimal(1010), "description1"));
                save(new Product(null, "product3", new BigDecimal(1500), "description1"));
                save(new Product(null, "product4", new BigDecimal(1570), "description1"));
                ut.commit();
            } catch (Exception ex) {
                try {
                    ut.rollback();
                } catch (SystemException e) {
                    e.printStackTrace();
                }
                throw new RuntimeException(ex);
            }
        }
    }

    @Transactional
    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        }
        em.merge(product);
    }


    @Transactional
    public void delete(Long id) {
        em.createNamedQuery("deleteProductId").setParameter("id", id).executeUpdate();
    }

    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createNamedQuery("findAllProduct", Product.class).getResultList();

    }

    public long count() {
        return em.createNamedQuery("count", Long.class).getSingleResult();
    }
}
