package com.study.microservices.studyapplication.jpa;

import com.study.microservices.studyapplication.domain.model.Kitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class InsertKitchen {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Kitchen> listar() {
        return entityManager.createQuery("from Kitchen", Kitchen.class).getResultList();
    }

    @Transactional
    public Kitchen add(Kitchen kitchen) {
        return entityManager.merge(kitchen);
    }

    public Kitchen find(Long id) {
        return entityManager.find(Kitchen.class, id);
    }

    @Transactional
    public void remove(Kitchen kitchen) {
        kitchen = find(kitchen.getId());
        entityManager.remove(kitchen);
    }

}
