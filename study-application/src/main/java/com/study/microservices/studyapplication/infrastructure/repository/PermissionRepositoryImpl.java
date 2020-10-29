package com.study.microservices.studyapplication.infrastructure.repository;

import com.study.microservices.studyapplication.domain.model.Permission;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PermissionRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Permission> findAll() {
        return entityManager.createQuery("from Permission", Permission.class).getResultList();
    }

    public Permission findById(final Long id) {
        return entityManager.find(Permission.class, id);
    }

    @Transactional
    public Permission save(final Permission permission) {
        return entityManager.merge(permission);
    }

    @Transactional
    public void delete(final Permission permission) {
        Permission permissionBase = findById(permission.getId());
        entityManager.remove(permissionBase);
    }
}
