package ru.geekbrains.persist;

import javax.inject.Named;
import javax.persistence.*;

@Entity
@Table(name = "customers")
@NamedQueries({
        @NamedQuery(name = "deleteCustomerById", query = "delete from Customer c where c.id = :id"),
        @NamedQuery(name = "findAllCustomer", query = "from Customer c"),
        @NamedQuery(name = "count", query = "select count(c) from Customer c")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String phone;

    public Customer( ) {
    }

    public Customer(Long id, String name,  String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
