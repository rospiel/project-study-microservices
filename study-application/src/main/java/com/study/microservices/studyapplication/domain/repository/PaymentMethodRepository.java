package com.study.microservices.studyapplication.domain.repository;

import com.study.microservices.studyapplication.domain.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> { }
