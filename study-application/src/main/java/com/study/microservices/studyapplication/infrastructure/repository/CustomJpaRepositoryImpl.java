package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static java.util.Optional.ofNullable;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager entityManager;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<T> findFirst() {
        StringBuilder jpql = new StringBuilder("from ");
        jpql.append(getDomainClass().getName());
        return ofNullable(entityManager.createQuery(jpql.toString(), getDomainClass())
                .setMaxResults(1)
                .getSingleResult());
    }
}
