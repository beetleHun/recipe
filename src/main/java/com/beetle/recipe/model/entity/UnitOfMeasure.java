package com.beetle.recipe.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit_of_measure")
public class UnitOfMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit")
    private String unit;

    public Long getId() {
        return id;
    }

    public String getUnit() {
        return unit;
    }

    public UnitOfMeasure setUnit(String unit) {
        this.unit = unit;
        return this;
    }

}
