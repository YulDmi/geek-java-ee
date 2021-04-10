package ru.geekbrains.persist;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.util.List;
@Named
@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    @Resource
    private UserTransaction ut;

    @PostConstruct
    public void init() {
        if (count() == 0) {
            try {
                ut.begin();
                save(new Category(null, "goods"));
                save(new Category(null, "food"));
                ut.commit();
            } catch (Exception ex) {
                try {
                    ut.rollback();
                } catch (SystemException e) {
                    throw new RuntimeException(e);
                }
                throw new RuntimeException(ex);
            }
        }
    }
    @Transactional
    public void save(Category category) {
        if (category.getId() == null) {
            em.persist(category);
        }
        em.merge(category);
    }
    @Transactional
    public void delete(Long id) {
        em.createNamedQuery("deleteCategoryId").setParameter("id", id).executeUpdate();
    }

    public Category findById(Long id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createNamedQuery("findAllCategory", Category.class).getResultList();

    }

    public long count() {
        return em.createNamedQuery("countCategory", Long.class).getSingleResult();
    }
}
