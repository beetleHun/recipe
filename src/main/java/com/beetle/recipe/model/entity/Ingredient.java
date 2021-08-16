package com.beetle.recipe.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToMany(mappedBy = "ingredients")
    // It is not necessary to define the same join table on this side of the relation.
    // If the mappedBy property is set, Hibernate will make the relation automatically.
//    @JoinTable(
//            name = "recipe_ingredient",
//            joinColumns = @JoinColumn(name = "ingredient_id"),
//            inverseJoinColumns = @JoinColumn(name = "recipe_id")
//    )
    private Set<Recipe> recipes;

    @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "unit_id")
    private UnitOfMeasure unitOfMeasure;

}
