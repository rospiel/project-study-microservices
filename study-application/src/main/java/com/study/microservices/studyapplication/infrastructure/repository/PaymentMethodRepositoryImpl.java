package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.PaymentMethod;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PaymentMethodRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PaymentMethod> findAll() {
        return entityManager.createQuery(" from PaymentMethod", PaymentMethod.class).getResultList();
    }

    public PaymentMethod findById(final Long id) {
        return entityManager.find(PaymentMethod.class, id);
    }

    @Transactional
    public PaymentMethod save(final PaymentMethod paymentMethod) {
        return entityManager.merge(paymentMethod);
    }

    @Transactional
    public void delete(final PaymentMethod paymentMethod) {
        PaymentMethod paymentMethodBase = findById(paymentMethod.getId());
        entityManager.remove(paymentMethodBase);
    }
}
