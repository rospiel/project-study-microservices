package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.State;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class StateRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<State> findAll() {
        return entityManager.createQuery("from State", State.class).getResultList();
    }

    public State findById(final Long id) {
        return entityManager.find(State.class, id);
    }

    @Transactional
    public State save(final State state) {
        return entityManager.merge(state);
    }

    @Transactional
    public void delete(final State state) {
        State stateBase = findById(state.getId());
        entityManager.remove(stateBase);
    }
}
