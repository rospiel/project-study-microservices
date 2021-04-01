package com.study.microservices.studyapplication.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable =  false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal freightRate;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateCreate;

    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateConfirmation;

    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateCancellation;

    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateDeliver;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_client_id", nullable = false)
    private User client;

    @Embedded
    private Address addressDeliver;

    private EnumStatusOrder statusOrder;

    @OneToMany(fetch = LAZY, mappedBy = "order")
    private List<ItemOrder> itemOrders = new ArrayList<>();

}
