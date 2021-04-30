package ru.geekbrains.persist;

import ru.geekbrains.service.repr.RoleRepr;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(){

    }

    public Role(String name) {
        this.name = name;
    }

    public Role (RoleRepr roleRepr){
        this.id = roleRepr.getId();
        this.name = roleRepr.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
