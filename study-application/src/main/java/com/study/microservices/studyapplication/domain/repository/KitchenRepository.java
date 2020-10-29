package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.model.Kitchen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long> {

    Optional<List<Kitchen>> findByName(String name);
}
