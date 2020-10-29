package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> { }
