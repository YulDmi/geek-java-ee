package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.math.BigDecimal;
import java.util.List;

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
                save(new Product(null, "product1", new BigDecimal(1000), "description1", new Category(1L, "category1")));
                save(new Product(null, "product2", new BigDecimal(1010), "description1", new Category(1L, "category1")));
                save(new Product(null, "product3", new BigDecimal(1500), "description1", new Category(2L, "category2")));
                save(new Product(null, "product4", new BigDecimal(1570), "description1", new Category(2L, "category2")));
                save(new Product(null, "product5", new BigDecimal(15), "description5", new Category(2L, "category2")));
                ut.commit();
            } catch (Exception ex) {
                try {
                    ut.rollback();
                } catch (SystemException e) {
                    throw new RuntimeException(e);
                }
                throw new RuntimeException(ex);
            }
        }
    }


    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        }
        em.merge(product);
    }


    public void delete(Long id) {
        em.createNamedQuery("deleteProductById").setParameter("id", id).executeUpdate();
    }


    public Product findById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createNamedQuery("findAllProduct", Product.class).getResultList();

    }

    public long count() {
        return em.createNamedQuery("countProduct", Long.class).getSingleResult();
    }
}
