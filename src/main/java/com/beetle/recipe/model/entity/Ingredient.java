package com.beetle.recipe.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @Override
    public String toString() {
        BigDecimal readableAmount = amount.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros();

        return readableAmount + " " + unitOfMeasure.getMeasure().toLowerCase() +
                (readableAmount.compareTo(BigDecimal.ONE) > 0 ? "s" : "") +
                " of " + description;
    }

}
