package com.study.microservices.studyapplication.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class City {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    @NonNull
    private String name;

    @Column(name = "user")
    @LastModifiedBy
    private String modifiedBy;

    @Column(name = "user_created")
    @CreatedBy
    private String createdBy;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "state_id")
    private State state;

    public City(Long id) {
        this.id = id;
    }

    public City(Long id, @NonNull String name, State state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }
}
