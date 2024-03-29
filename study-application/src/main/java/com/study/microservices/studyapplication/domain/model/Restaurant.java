package com.study.microservices.studyapplication.domain.model;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.googlecode.jmapper.annotations.JMap;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Embedded;
import javax.persistence.PrePersist;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.UUID.randomUUID;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class  Restaurant {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String code;

    /* by default the length is 255*/
    @Column(nullable = false)
    @NotBlank
    private String name;

    /* by default the length is 255*/
    @Column
    @DecimalMin(value = "0")
    private BigDecimal freightRate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "kitchen_id", nullable = false)
    @NotNull
    private Kitchen kitchen;

    @ManyToMany
    @JoinTable(name = "restaurant_payment_method",
               joinColumns = @JoinColumn(name = "restaurant_id"),
               inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
    private Set<PaymentMethod> paymentMethods = new HashSet<>();

    @Embedded
    private Address address;

    @Column(nullable = false)
    private Boolean enable = TRUE;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateCreate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dateUpdate;

    public Restaurant(Long id) {
        this.id = id;
    }

    public Restaurant(String name, BigDecimal freightRate, Kitchen kitchen) {
        this.name = name;
        this.freightRate = freightRate;
        this.kitchen = kitchen;
    }

    public Restaurant(Long id, Set<PaymentMethod> paymentMethods) {
        this.id = id;
        this.paymentMethods = paymentMethods;
    }

    @PrePersist
    private void generateCode() {
        setCode(randomUUID().toString());
    }
}
