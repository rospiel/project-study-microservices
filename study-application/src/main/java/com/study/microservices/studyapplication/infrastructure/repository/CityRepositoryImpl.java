package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.City;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CityRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<City> findAll() {
        return entityManager.createQuery("from City", City.class).getResultList();
    }

    public City findById(final Long id) {
        return entityManager.find(City.class, id);
    }

    @Transactional
    public City save(final City city) {
        return entityManager.merge(city);
    }

    @Transactional
    public void delete(final City city) {
        City cityBase = findById(city.getId());
        entityManager.remove(cityBase);
    }
}
