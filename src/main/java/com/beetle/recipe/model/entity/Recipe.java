package com.beetle.recipe.model.entity;

import com.beetle.recipe.model.enums.Difficulty;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "preparation_time")
    private Integer prepTime;

    @Column(name = "cook_time")
    private Integer cookTime;

    @Column(name = "servings")
    private Integer servings;

    @Column(name = "source")
    private String source;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "directions")
    private String directions;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    @Column(name = "image")
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNote(Note note) {
        if (note != null) {
            note.setRecipe(this);
            this.note = note;
        }
    }

    public void addCategory(Category category) {
        category.getRecipes().add(this);
        this.categories.add(category);
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
    }

}
