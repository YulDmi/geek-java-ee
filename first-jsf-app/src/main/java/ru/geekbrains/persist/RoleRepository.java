package ru.geekbrains.persist;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RoleRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public Role merge(Role role){

        if (role.getId() == null) {
            em.persist(role);
            return role;
        }

        return em.merge(role);
    }

    @TransactionAttribute
    public void delete(long id){
        try {
            Role role = findById(id);
            if(role != null){
                em.remove(role);
            }
        }catch (Exception ex){
            throw new IllegalStateException(ex);
        }

    }

    public Role findById(long id){
        return em.find(Role.class, id);
    }

    public List<Role> getAllRoles(){
        return em.createQuery("from Role", Role.class).getResultList();
    }

    public long getCount(){
        return em.createNamedQuery("select count(*) from Role", Long.class).getSingleResult();
    }
}
