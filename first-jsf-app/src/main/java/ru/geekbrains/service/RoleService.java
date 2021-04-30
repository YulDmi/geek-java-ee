package ru.geekbrains.service;

import ru.geekbrains.persist.Role;
import ru.geekbrains.persist.RoleRepository;
import ru.geekbrains.service.repr.RoleRepr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class RoleService implements Serializable {

    @Inject
    private RoleRepository roleRepository;

    public List<RoleRepr> getAllRoles() {
        return roleRepository.getAllRoles().stream()
                .map(RoleRepr::new)
                .collect(Collectors.toList());
    }

    public void   save(RoleRepr roleRepr){
        roleRepository.merge(new Role(roleRepr));
    }

    public void delete(long id){
        roleRepository.delete(id);
    }
}
