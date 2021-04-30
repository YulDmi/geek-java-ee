package ru.geekbrains.controller;

import ru.geekbrains.service.RoleService;
import ru.geekbrains.service.UserService;
import ru.geekbrains.service.repr.RoleRepr;
import ru.geekbrains.service.repr.UserRepr;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.List;


@SessionScoped
@Named
public class UserController implements Serializable {

    @EJB
    private UserService userService;

    @EJB
    private RoleService roleService;

    @Inject
    private HttpSession session;

    private UserRepr user;
    private List<UserRepr> users;
    private List<RoleRepr> roles;

    public void preLoad() {
        this.roles = roleService.getAllRoles();
        this.users = userService.getAllUsers();
    }

    public UserRepr getUser(){
        return user;
    }

    public void setUser(UserRepr user){
        this.user = user;
    }

    public List<UserRepr> getAllUsers() {
        return users;
    }

    public String editUser(UserRepr user){
        this.user = user;
        return "/admin/user_form.xhtml?faces-redirect = true";
    }

    public void deleteUser(UserRepr user) {
        userService.delete(user.getId());
    }

    public String createUser() {
        this.user = new UserRepr();
        return "/admin/user_form.xhtml?faces-redirect=true";
    }

    public String saveUser() {
        userService.saveOrUpdate(this.user);
        return "/admin/user.xhtml?faces-redirect=true";
    }


    public List<RoleRepr> getAllRoles() {
        return roles;
    }

    public String logout() {
        session.invalidate();
        return "/product.xhtml?faces-redirect=true";
    }
}
