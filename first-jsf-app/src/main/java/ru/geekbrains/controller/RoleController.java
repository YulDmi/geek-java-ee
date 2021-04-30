package ru.geekbrains.controller;

import ru.geekbrains.service.CategoryService;
import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.repr.CategoryRepr;
import ru.geekbrains.service.repr.RoleRepr;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class RoleController implements Serializable {

    @Inject
    private RoleService roleService;

    private RoleRepr role;
    private List<RoleRepr> roleList;

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.roleList = roleService.getAllRoles();
    }

    public RoleRepr getRole() {
        return role;
    }

    public void setRole(RoleRepr role) {
        this.role = role;
    }

    public List<RoleRepr> findAll() {
        return roleList;
    }

    public String editRole(RoleRepr role) {
        this.role = role;
        return "/role_form.xhtml?faces-redirect=true";
    }

    public void deleteRole(RoleRepr role) {
        roleService.delete(role.getId());
    }

    public String saveRole() {
        roleService.save(role);
        return "/role.xhtml?faces-redirect=true";
    }

    public String addRole() {
        this.role = new RoleRepr();
        return "/role_form.xhtml?faces-redirect=true";
    }
}
