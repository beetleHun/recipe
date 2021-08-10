package com.beetle.recipe.model.entity;

import com.beetle.recipe.model.enums.Difficulty;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

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

    @Column(name = "directions")
    private String directions;

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    @Column(name = "difficulty")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToMany
    @JoinTable(name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    // Originally the spring-recipe-project suggests one-to-many relation, but it makes more sense to me to use it
    // as many-to-many. Multiple recipes can have the same subset of ingredients, and those can be necessary
    // to other recipes as well.
    // @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    // Originally the spring-recipe suggests many-to-many relationship, but it does not makes sanes to me,
    // how could a recipe have multiple categories? How can it be soup and dessert as well
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Recipe setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public Recipe setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public Recipe setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public Integer getServings() {
        return servings;
    }

    public Recipe setServings(Integer servings) {
        this.servings = servings;
        return this;
    }

    public String getSource() {
        return source;
    }

    public Recipe setSource(String source) {
        this.source = source;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Recipe setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDirections() {
        return directions;
    }

    public Recipe setDirections(String directions) {
        this.directions = directions;
        return this;
    }

    public Note getNote() {
        return note;
    }

    public Recipe setNote(Note note) {
        this.note = note;
        return this;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Recipe setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public byte[] getImage() {
        return image;
    }

    public Recipe setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Recipe setCategory(Category category) {
        this.category = category;
        return this;
    }

}
