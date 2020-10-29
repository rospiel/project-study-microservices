package com.study.microservices.studyapplication.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Data
@Embeddable
public class Address {

    @Column(name = "address_zip_code")
    private String zipCode;

    @Column(name = "address_street")
    private String street;

    @Column(name = "address_number")
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood")
    private String neighborhood;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city")
    private City city;

}
