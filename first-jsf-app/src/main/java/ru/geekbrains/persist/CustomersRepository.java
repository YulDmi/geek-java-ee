package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
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

    private final Map<Long, Customer> customers = new ConcurrentHashMap<>();
    private final AtomicLong identity = new AtomicLong(0);

    public void save(Customer customer) {
        if (customer.getId() == null) {
            customer.setId(identity.incrementAndGet());
        }
        customers.put(customer.getId(), customer);
    }

    @PostConstruct
    public void init() {
        save(new Customer(null, "Bob", "123456789"));
        save(new Customer(null, "Ivan", "223456789"));
        save(new Customer(null, "Tom", "55555555"));

    }

    public void delete(Long id) {
        customers.remove(id);
    }

    public Customer findById(Long id) {
        return customers.get(id);
    }

    public List<Customer> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(customers.values()));

    }
}
