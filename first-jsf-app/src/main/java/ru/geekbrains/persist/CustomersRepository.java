package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
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
public class CustomersRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    @PostConstruct
    public void init() {
        if (count() == 0) {
            try {
                ut.begin();
                save(new Customer(null, "Bob", "123456789"));
                save(new Customer(null, "Ivan", "223456789"));
                save(new Customer(null, "Tom", "55555555"));
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
    public void save(Customer customer) {
        if (customer.getId() == null) {
            em.persist(customer);
        }
        em.merge(customer);
    }


    @Transactional
    public void delete(Long id) {
        em.createNamedQuery("deleteCustomerId").setParameter("id", id).executeUpdate();
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createNamedQuery("findAllCustomer", Customer.class).getResultList();

    }

    public long count() {
        return em.createNamedQuery("count", Long.class).getSingleResult();
    }


}
