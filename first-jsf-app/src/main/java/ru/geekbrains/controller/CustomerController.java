package ru.geekbrains.controller;

import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomersRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CustomerController implements Serializable {

    @Inject
    private CustomersRepository customersRepository;
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> findAll(){
      return   customersRepository.findAll();
    }

    public String editCustomer(Customer customer) {
        this.customer = customer;
        return "/customer_form.xhtml?faces-redirect=true";
    }

    public void deleteCustomer(Customer customer) {
        customersRepository.delete(customer.getId());
    }

    public String saveCustomer() {
        customersRepository.save(customer);
        return "/customer.xhtml?faces-redirect=true";
    }

    public String addCustomer() {
        this.customer = new Customer();
        return "/customer_form.xhtml?faces-redirect=true";
    }
}
