package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.Kitchen;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class KitchenRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String FROM = "from Kitchen ";

    public List<Kitchen> findAll() {
        return entityManager.createQuery(FROM, Kitchen.class).getResultList();
    }

    public List<Kitchen> findByName(String name) {
        return entityManager.createQuery(FROM + "where name like :name", Kitchen.class)
                .setParameter("name", "%"+ name + "%")
                .getResultList();
    }

    public Kitchen findById(final Long id) {
        return entityManager.find(Kitchen.class, id);
    }

    @Transactional
    public Kitchen save(final Kitchen kitchen) {
        return entityManager.merge(kitchen);
    }

    @Transactional
    public void delete(final Kitchen kitchen) {
        Kitchen kitchenBase = findById(kitchen.getId());
        entityManager.remove(kitchenBase);
    }
}
