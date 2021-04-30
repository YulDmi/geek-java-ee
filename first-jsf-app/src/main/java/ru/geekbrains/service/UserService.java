package ru.geekbrains.service;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;
import ru.geekbrains.service.repr.UserRepr;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class UserService implements Serializable {

    @EJB
    private UserRepository userRepository;

    @TransactionAttribute
    public void saveOrUpdate(UserRepr userRepr) {
        User user = userRepository.saveOrUpdate(new User(userRepr));
        userRepr.setId(user.getId());
    }

   // @RolesAllowed({"ADMIN"})
    @TransactionAttribute
    public void delete(long id) {
        userRepository.delete(id);
    }

    @TransactionAttribute
    public UserRepr findById(int id) {
        return new UserRepr(userRepository.findById(id));
    }

    @TransactionAttribute
    public boolean existsById(int id) {
        return userRepository.findById(id) != null;
    }

    @TransactionAttribute
    public List<UserRepr> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }
}
