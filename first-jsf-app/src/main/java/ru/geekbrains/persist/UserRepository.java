package ru.geekbrains.persist;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class UserRepository {

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public User saveOrUpdate(User user){
        if(user.getId() == null){
            em.persist((user));
            return user;
        }
        return em.merge(user);
    }

    @TransactionAttribute
    public void delete(long id){
        try {
            User attached = findById(id);
            if(attached != null){
                em.remove(attached);
            }
        }catch (Exception ex){
            throw new IllegalStateException(ex);
        }

    }


    public User findById(long id){
        return em.find(User.class, id);
    }

    public boolean existById(long id){
        return findById(id) != null;
    }

    @TransactionAttribute
    public List<User> getAllUsers(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        from.fetch("roles", JoinType.LEFT);
        query.select(from).distinct(true);
        return em.createQuery(query).getResultList();
    }

    public long getCount(){
        return em.createNamedQuery("select count(*) from User", Long.class).getSingleResult();
    }

}
