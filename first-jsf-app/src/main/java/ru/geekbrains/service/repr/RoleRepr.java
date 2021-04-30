package ru.geekbrains.service.repr;

import ru.geekbrains.persist.Role;

import java.io.Serializable;
import java.util.Objects;

public class RoleRepr implements Serializable {

    private long id;
    private String name;

    public RoleRepr(){
    }


    public RoleRepr(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public long getId() {
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

    @Override
    public String toString() {
        return name;
    }
}
