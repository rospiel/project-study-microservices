package com.study.microservices.studyapplication.domain.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.study.microservices.studyapplication.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonRootName("kitchen") /* just to xml response */
public class Kitchen {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    @NotNull(groups = Groups.IncludeRestaurant.class)
    private Long id;

    @Column
    @NotBlank
    private String name;

    public Kitchen(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
